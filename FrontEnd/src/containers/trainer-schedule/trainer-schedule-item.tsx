import styled from "styled-components";
import { TrainerScheduleData } from "src/interface";
import WriteExerciseLog from "@user-trainer/write-exercise-log";
import { TchaButton, GreenTchaButton, TchaButtonTextH6 } from "@shared/button";
import { useState } from "react";
import { RootState } from "src/redux/store";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const Wrapper = styled.div`
  /* display:flex; */
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
  align-items: end;
  width: 100%;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 40%;
  @media (max-width: 767px) {
    min-width: 30%;
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
  margin: 0%;
  height: 3rem;
  width: 7rem;
  @media (max-width: 767px) {
    width: 2rem;
  }
`;

function TrainerScheduleItem(props: { data: TrainerScheduleData }) {
  const [item, setItem] = useState<TrainerScheduleData>(props.data);
  const trainer = useSelector((state: RootState) => state.profile.trainerId);
  const navigate = useNavigate();

  const goToPtRoom = (ptLiveId: number) => {
    navigate("/pt", { state: ptLiveId });
  };

  return (
    <Wrapper>
      <DataWrapper>
        <TimeWrapper>
          <StyledTextH4>{item.startDate}</StyledTextH4>
          <StyledTextH5>{item.startTime}</StyledTextH5>
        </TimeWrapper>
        <ButtonWrapper>
          <StyledButton>
            {!item.liveId && <TchaButtonTextH6>예약없음</TchaButtonTextH6>}
            {item.status === "INACCESSIBLE" && item.liveId && (
              <TchaButtonTextH6>입장 불가</TchaButtonTextH6>
            )}
            {(item.status === "ACCESSIBLE" || item.status === "TERMINABLE") && (
              <TchaButtonTextH6 onClick={() => goToPtRoom(item.liveId)}>
                PT 방 입장
              </TchaButtonTextH6>
            )}
          </StyledButton>
          <StyledButton>
            {item.status === "TERMINATION" && !item.liveId && (
              <WriteExerciseLog liveId={item.liveId} />
            )}
          </StyledButton>
        </ButtonWrapper>
      </DataWrapper>
    </Wrapper>
  );
}

export default TrainerScheduleItem;
