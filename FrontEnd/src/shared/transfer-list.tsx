import * as React from "react";
import Grid from "@mui/material/Grid";
import List from "@mui/material/List";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import ListItem from "@mui/material/ListItem";
import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  /* justify-content: space-between; */
  width: 100%;
  margin-top: 10%;
  /* background-color: #c6deec; */
  /* box-shadow: 3px 3px 5psx rgba(0, 0, 0, 0.1); */
  @media (max-width: 767px) {
    /* box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1); */
    margin-top: 5%;
  }
`;
const StyledCard = styled(Card)`
  display: flex;
  flex-direction: column;

  @media (max-width: 767px) {
    width: 30vw !important;
    height: 20rem !important;
  }
`;

const StyledList = styled(List)`
  display: flex;
  flex-direction: column;
  @media (max-width: 767px) {
    width: 30vw !important;
    height: 20rem !important;
  }
`;
const StyledButton = styled(Button)`
  /* display: flex ; */
`;

function not(a: readonly string[], b: readonly string[]) {
  return a.filter((value) => b.indexOf(value) === -1);
}

function intersection(a: readonly string[], b: readonly string[]) {
  return a.filter((value) => b.indexOf(value) !== -1);
}

interface TransferListProps {
  times: string[];
  handleChangeList: (items: string[]) => void;
}

export default function TransferList({
  times,
  handleChangeList,
}: TransferListProps) {
  const [checked, setChecked] = React.useState<readonly string[]>([]);
  const [left, setLeft] = React.useState<readonly string[]>(times);
  const [right, setRight] = React.useState<string[]>([]);
  const leftChecked = intersection(checked, left);
  const rightChecked = intersection(checked, right);

  React.useEffect(() => {
    setLeft(times);
  }, [times]);

  React.useEffect(() => {
    handleChangeList(right);
  }, [handleChangeList, right]);

  const [disabledValueList, setDisabledValueList] = React.useState<string[]>(
    []
  );

  const handleToggle = (value: string, index: number) => {
    if (checked.includes(value)) {
      setChecked((prevChecked) => prevChecked.filter((item) => item !== value));
      if (left.includes(value)) {
        if (index + 1 < left.length) {
          const nextButton = left[index + 1];
          if (!checked.includes(left[index + 2])) {
            setDisabledValueList((disabledValueList) =>
              disabledValueList.filter((item) => item !== nextButton)
            );
          }
        }
        if (index - 1 >= 0) {
          const prevButton = left[index - 1];
          if (!checked.includes(left[index - 2])) {
            setDisabledValueList((disabledValueList) =>
              disabledValueList.filter((item) => item !== prevButton)
            );
          }
        }
      }
    } else {
      setChecked((prevChecked) => [...prevChecked, value]);
      if (left.includes(value)) {
        if (index + 1 < left.length) {
          const nextButton = left[index + 1];
          setDisabledValueList((disabledValueList) => [
            ...disabledValueList,
            nextButton,
          ]);
        }
        if (index - 1 >= 0) {
          const prevButton = left[index - 1];
          setDisabledValueList((disabledValueList) => [
            ...disabledValueList,
            prevButton,
          ]);
        }
      }
    }
  };

  //정렬 comp함수
  const timeSorter = (a: string, b: string) => {
    const [aHour, aMinute] = a.split(":").map(Number);
    const [bHour, bMinute] = b.split(":").map(Number);

    if (aHour !== bHour) {
      return aHour - bHour;
    }
    return aMinute - bMinute;
  };

  // 30분 차이가 나는지 확인하는 도우미 함수
  function isHalfHourDifference(time1: string, time2: string): boolean {
    const [hour1, min1] = time1.split(":").map(Number);
    const [hour2, min2] = time2.split(":").map(Number);

    const calMin1 = hour1 * 60 + min1;
    const calMin2 = hour2 * 60 + min2;

    const diff = Math.abs(calMin1 - calMin2);

    return diff === 30;
  }

  const numberOfChecked = (items: readonly string[]) =>
    intersection(checked, items).length;

  const handleCheckedRight = () => {
    setRight(right.concat(leftChecked).sort(timeSorter));
    setLeft(not(left, leftChecked));
    setChecked(not(checked, leftChecked));
  };

  const handleCheckedLeft = () => {
    //오른쪽에서 체크한 것들과 왼쪽에 있던것들을 합하고 정렬함
    const templeft = left.concat(rightChecked).sort(timeSorter);
    //그것을 Left에 반영할거임
    setLeft(templeft);

    const rightNotChecked = not(right, rightChecked);

    templeft.forEach((value, index) => {
      //오른쪽에서 체크해서 넘어온 것의 index를 구하고, 앞뒤 index도 구함
      if (rightChecked.includes(value)) {
        // const stateChangeNextButton = templeft[index+1];
        // const stateChangePrevButton = templeft[index-1];
        //넘어올때 앞뒤 index 비활성화됐던것도 해제함
        //근데 문제가 생김 -> ex)1시와 2시를 오른쪽에 넣었다가 2시를 해제하면,
        //변동된 templeft 기준으로 해제하는데 1시와 겹치는 시간(1시반)도 해제함
        // if (disabledValueList.includes(stateChangeNextButton)){
        //   setDisabledValueList(disabledValueList => disabledValueList.filter(item => item !== stateChangeNextButton))
        // }
        // if (disabledValueList.includes(stateChangePrevButton)){
        //   setDisabledValueList(disabledValueList => disabledValueList.filter(item => item !== stateChangePrevButton))
        // }

        //대안:
        //만약 오른쪽에서 왼쪽으로 넘길때, 해당 앞뒤시간도 원래 disable상태 해제해줘야 하는데
        //그 앞뒤시간이 오른쪽 잔여리스트와 30분 차이나지 않을때만 able상태로 전환해줌
        if (index - 1 >= 0) {
          const stateChangePrevButton = templeft[index - 1];
          const IfHalfDiffinRightPrev = rightNotChecked.some((item) =>
            isHalfHourDifference(stateChangePrevButton, item)
          );
          if (
            disabledValueList.includes(stateChangePrevButton) &&
            !IfHalfDiffinRightPrev
          ) {
            setDisabledValueList((disabledValueList) =>
              disabledValueList.filter((item) => item !== stateChangePrevButton)
            );
          }
        }

        if (index + 1 < left.length) {
          const stateChangeNextButton = templeft[index + 1];
          const IfHalfDiffinRightNext = rightNotChecked.some((item) =>
            isHalfHourDifference(stateChangeNextButton, item)
          );
          if (
            disabledValueList.includes(stateChangeNextButton) &&
            !IfHalfDiffinRightNext
          ) {
            setDisabledValueList((disabledValueList) =>
              disabledValueList.filter((item) => item !== stateChangeNextButton)
            );
          }
        }
      }
    });
    setRight(rightNotChecked);
    setChecked(not(checked, rightChecked));
  };

  const customList = (title: React.ReactNode, items: readonly string[]) => (
    <StyledCard>
      <CardHeader
        sx={{ px: 2, py: 1 }}
        title={title}
        subheader={`Total ${numberOfChecked(items)}/${items.length}`}
        // style={{backgroundColor:"lightgray"}}
        style={{ backgroundColor: "#70b4b1" }}
      />
      <Divider />
      <StyledList
        sx={{
          width: 200,
          height: 230,
          bgcolor: "background.paper",
          overflow: "auto",
        }}
        dense
        // component="div"
        role="list"
      >
        {items.map((value: string, index: number) => {
          return (
            <ListItem key={index} role="listitem">
              <StyledButton
                variant={checked.includes(value) ? "contained" : "outlined"}
                color={"success"}
                // color={checked.includes(value) ? "secondary" : "primary"}
                onClick={() => handleToggle(value, index)}
                disabled={disabledValueList.includes(value)}
              >
                {value}
              </StyledButton>
            </ListItem>
          );
        })}
      </StyledList>
    </StyledCard>
  );

  return (
    <Wrapper>
      <Grid container spacing={2} justifyContent="center" alignItems="center">
        <Grid item>{customList("Choices", left)}</Grid>
        <Grid item>
          <Grid container direction="column" alignItems="center">
            <Button
              sx={{ my: 0.5 }}
              variant={leftChecked.length ? "contained" : "outlined"}
              style={{
                backgroundColor: leftChecked.length ? "#6E7783" : "#ebebeb",
              }}
              // color={leftChecked.length ? "secondary" : "primary"}
              size="small"
              onClick={handleCheckedRight}
              disabled={leftChecked.length === 0}
              aria-label="move selected right"
            >
              &gt;
            </Button>
            <Button
              sx={{ my: 0.5 }}
              style={{
                backgroundColor: rightChecked.length ? "#6E7783" : "#ebebeb",
              }}
              variant={rightChecked.length ? "contained" : "outlined"}
              size="small"
              onClick={handleCheckedLeft}
              disabled={rightChecked.length === 0}
              aria-label="move selected left"
            >
              &lt;
            </Button>
          </Grid>
        </Grid>
        <Grid item>{customList("Chosen", right)}</Grid>
      </Grid>
    </Wrapper>
  );
}
