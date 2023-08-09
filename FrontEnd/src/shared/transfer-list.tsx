import * as React from 'react';
import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import ListItem from '@mui/material/ListItem';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';

function not(a: readonly string[], b: readonly string[]) {
  return a.filter((value) => b.indexOf(value) === -1);
}

function intersection(a: readonly string[], b: readonly string[]) {
  return a.filter((value) => b.indexOf(value) !== -1);
}

function union(a: readonly string[], b: readonly string[]) {
  return [...a, ...not(b, a)];
}

interface TransferListProps {
  times: string[];
}



export default function TransferList({ times }: TransferListProps) {
  const [checked, setChecked] = React.useState<readonly string[]>([]);
  // const midIndex = Math.floor(times.length / 2);
  const [left, setLeft] = React.useState<readonly string[]>(times.slice(0, times.length+1));
  const [right, setRight] = React.useState<readonly string[]>([]);

  const leftChecked = intersection(checked, left);
  const rightChecked = intersection(checked, right);

  //시간 계산해서 겹치는시간 제거하는 로직 
  const calRemoveTimes = (selectedTime: string) => {
    let [hour, minute] = selectedTime.split(":").map(Number);
    let returnTimes = [];
  

    if (minute === 30) {
      minute = 0;
      hour += 1;
    } else {
      minute = 30;
    }
    if (hour < 24) {
      const timeStr = `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
      returnTimes.push(timeStr);
    }

    return returnTimes;
  };

  const handleToggle = (value: string) => {
    if (checked.includes(value)) {
      setChecked(prevChecked => prevChecked.filter(item => item !== value));
    } else {
      setChecked(prevChecked => [...prevChecked, value]);
    }
  };
//   const handleToggle = (value: string) => {
//   const adjacentTimes = calRemoveTimes(value);
//   if (checked.includes(value)) {
//     // 해제하는 경우
//     setChecked(prevChecked => prevChecked.filter(item => item !== value));
//     setLeft(prevLeft => [...prevLeft, ...adjacentTimes].sort());
//   } else {
//     // 선택하는 경우
//     setChecked(prevChecked => [...prevChecked, value]);
//     setLeft(prevLeft => prevLeft.filter(item => !adjacentTimes.includes(item)));
//   }
// };


  const numberOfChecked = (items: readonly string[]) =>
    intersection(checked, items).length;

  const handleToggleAll = (items: readonly string[]) => () => {
    if (numberOfChecked(items) === items.length) {
      setChecked(not(checked, items));
    } else {
      setChecked(union(checked, items));
    }
  };

  const handleCheckedRight = () => {
    setRight(right.concat(leftChecked));
    setLeft(not(left, leftChecked));
    setChecked(not(checked, leftChecked));
  };

  const handleCheckedLeft = () => {
    setLeft(left.concat(rightChecked));
    setRight(not(right, rightChecked));
    setChecked(not(checked, rightChecked));
  };




  const customList = (title: React.ReactNode, items: readonly string[]) => (
    <Card>
      <CardHeader
        sx={{ px: 2, py: 1 }}
        title={title}
        subheader={`${numberOfChecked(items)}/${items.length} selected`}
      />
      <Divider />
      <List
        sx={{
          width: 200,
          height: 230,
          bgcolor: 'background.paper',
          overflow: 'auto',
        }}
        dense
        component="div"
        role="list"
      >
        {items.map((value: string,index) => {
          return (
            <ListItem key={index} role="listitem">
              <Button
                variant={
                  checked.includes(value) ? "contained" : "outlined"}
                // color={checked.includes(value) ? "secondary" : "primary"}
                onClick={() => handleToggle(value)}
              >
                {value}
              </Button>
            </ListItem>
          );
        })}
      </List>
    </Card>
  );


  return (
    <Grid container spacing={2} justifyContent="center" alignItems="center">
      <Grid item>{customList('Choices', left)}</Grid>
      <Grid item>
        <Grid container direction="column" alignItems="center">
          <Button
            sx={{ my: 0.5 }}
            variant="outlined"
            size="small"
            onClick={handleCheckedRight}
            disabled={leftChecked.length === 0}
            aria-label="move selected right"
          >
            &gt;
          </Button>
          <Button
            sx={{ my: 0.5 }}
            variant="outlined"
            size="small"
            onClick={handleCheckedLeft}
            disabled={rightChecked.length === 0}
            aria-label="move selected left"
          >
            &lt;
          </Button>
        </Grid>
      </Grid>
      <Grid item>{customList('Chosen', right)}</Grid>
    </Grid>
  );
}
