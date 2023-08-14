import styled from "styled-components";
import { UserScheduleData } from "src/interface";
import WriteReview from "../user-review/write-review";
import { TchaButton } from "@shared/button";
import { useState } from "react";

const Wrapper = styled.div`
  /* display:flex; */
  /* justify-content:center; */
  /* align-items: center; */
  height: 10rem;
  background-color: lightpink;
  margin-bottom: 1%;
  border-radius: 5px;
`;
const DataWrapper = styled.div`
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
`;

const TimeWrapper = styled.div`
  flex: 3;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const PtInfoWrapper = styled.div`
  flex: 6;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const TrainerName = styled.div``;

const ButtonWrapper = styled.div`
  flex: 3;
  display: flex;
  justify-content: center;
`;

function UserScheduleItem(props: { data: UserScheduleData }) {
  const [item, setItem] = useState<UserScheduleData>(props.data);
  return (
    <Wrapper>
      <DataWrapper>
        <TimeWrapper>
          <h3>Time: {item.startTime}</h3>
        </TimeWrapper>
        <PtInfoWrapper>
          <h2 style={{ marginTop: "2px", marginBottom: "2px" }}>
            {item.startDate}
          </h2>
          <TrainerName>
            <b style={{ fontSize: "0.5rem" }}>트레이너</b>
            <b style={{ fontSize: "1rem" }}> {item.trainerId}</b>
          </TrainerName>
        </PtInfoWrapper>
        <ButtonWrapper>
          {item.status === "INACCESSIBLE" && (
            <TchaButton disabled>지금은 입장이 불가능합니다</TchaButton>
          )}
          {(item.status === "ACCESSIBLE" || item.status === "TERMINABLE") && (
            <TchaButton disabled>PT 방 입장하기</TchaButton>
          )}
          {item.status === "TERMINATION" && !item.reviewId && (
            <WriteReview
              trainer={item.trainerId}
              liveId={item.liveId}
              setItem={setItem}
            />
          )}
        </ButtonWrapper>
      </DataWrapper>
    </Wrapper>
  );
}

export default UserScheduleItem;
