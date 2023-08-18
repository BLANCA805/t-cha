import styled from "styled-components";
import { UserScheduleData } from "src/interface";
import WriteReview from "../user-review/write-review";
import { TchaButton, GreenTchaButton, TchaButtonTextH6 } from "@shared/button";
import { useState } from "react";
import WriteExerciseLog from "@user-trainer/write-exercise-log";
import ReadExerciseLog from "@user-trainer/read-exercise-log";
import { useNavigate } from "react-router-dom";

const Wrapper = styled.div`
  /* display: flex; */
  /* justify-content:center; */
  /* align-items: center; */
  height: 10rem;
  width: 100%;
  width: 97%;
  background-color: lightpink;
  margin-bottom: 1%;
  border-radius: 5px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    height: 6rem;
  }
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
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 35%;
  @media (max-width: 767px) {
    margin: 0% 0.5%;
  }
`;

const PtInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

const TrainerName = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: start;
  align-items: center;
  width: 40%;
  margin-right: 1%;
  @media (max-width: 767px) {
    flex-direction:column;
    /* min-width: 30%; */
    max-width: 31%;
  }
`;

const StyledTextH6 = styled.h6`
  margin: 0%;
  font-size: 1rem;
  @media (max-width: 767px) {
    font-size: 0.7rem;
  }
`;
const StyledTextH5 = styled.h5`
  margin: 0%;
  font-size: 1rem;
  @media (max-width: 767px) {
    font-size: 0.7rem;
  }
`;
const StyledTextH4 = styled.h4`
  margin: 0% 0% 2% 0%;
  font-size: 1rem;
  @media (max-width: 767px) {
    font-size: 0.5rem;
  }
`;

const TrNameText = styled(StyledTextH4)`
  font-size: 2rem;
  margin-left: 2%;
  @media (max-width: 767px) {
    font-size: 1.3rem;
  }
`;
const TrText = styled(StyledTextH5)`
  font-size: 1.7rem;
  margin-left: 2%;
  @media (max-width: 767px) {
    font-size: 1rem;
  }
`;
const StyledButton = styled(GreenTchaButton)`
  margin-right: 0.5rem;
  height: 3rem;
  width: 7rem;
  @media (max-width: 767px) {
    width: 6rem;
    height:1.6rem;
  }
`;

function UserScheduleItem(props: { data: UserScheduleData }) {
  const [item, setItem] = useState<UserScheduleData>(props.data);
  const navigate = useNavigate();
  return (
    <Wrapper>
      <DataWrapper>
        <TimeWrapper>
          <StyledTextH4>{item.startDate}</StyledTextH4>
          <StyledTextH5>{item.startTime}</StyledTextH5>
        </TimeWrapper>
        <PtInfoWrapper>
          <TrainerName>
            <TrNameText> {item.trainerName}</TrNameText>
            <TrText>트레이너</TrText>
          </TrainerName>
        </PtInfoWrapper>
        <ButtonWrapper>
          {item.ptLiveStatus === "INACCESSIBLE" && (
            <StyledButton disabled>
              <TchaButtonTextH6>입장 불가</TchaButtonTextH6>
            </StyledButton>
          )}
          {/* {(item.ptLiveStatus === "ACCESSIBLE" ||
            item.ptLiveStatus === "TERMINABLE") && ( */}
          <StyledButton onClick={() => navigate("/pt", { state: item.liveId })}>
            <TchaButtonTextH6>PT 방 입장</TchaButtonTextH6>
          </StyledButton>
          {/* )} */}
          {/* {item.ptLiveStatus === "TERMINATION" && !item.reviewId && ( */}
          <WriteReview
            trainerName={item.trainerName}
            trainer={item.trainerId}
            liveId={item.liveId}
            setItem={setItem}
          />
          {/* )} */}
          {item.exerciseLogStatus === "WRITE" ? (
            <StyledButton disabled>
              <TchaButtonTextH6>일지 확인</TchaButtonTextH6>
            </StyledButton>
          ) : (
            <ReadExerciseLog liveId={item.liveId} />
          )}
        </ButtonWrapper>
      </DataWrapper>
    </Wrapper>
  );
}

export default UserScheduleItem;
