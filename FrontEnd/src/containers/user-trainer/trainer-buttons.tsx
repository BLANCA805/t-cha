import styled from "styled-components";
import { Link, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";


const ContainerSet = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin: 1% 0%;
  @media (max-width: 767px) {
   margin:2% 0%; 
  }
`;

const TrainerContainer = styled(ContainerSet)`
  display: flex;
  flex-direction: column;
  /* display:none; */
`;

const TrRow = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height:7.5rem;
  width: 100%;
  background-color: white;
  border-radius: 10px;
  margin: 0.3% 0%;
  cursor:pointer;
  &:first-child{
    margin-top:0%;
  }
  &:last-child{
    margin-bottom: 0%;
  }
  @media (max-width: 767px) {
    height:3rem; 
    margin: 0.5% 0%;
    border-radius: 4px;
  }
`;

const StyledTextBig = styled.h5`
  margin: 2% 0%;
  font-size:1.7rem;
  @media (max-width: 767px) {
    font-size:1rem; 
  }
`;

function TrainerButtons() {
  const trainerId = useSelector((state: RootState) => state.profile.trainerId);
  const navigate = useNavigate();
  return (
    <TrainerContainer>
      <TrRow onClick={() => navigate("/trainer/info", { state: trainerId  })}>
        <StyledTextBig> 트레이너 상세 페이지 </StyledTextBig>
      </TrRow>
      <TrRow onClick={() => navigate("trainer_schedule")}>
        <StyledTextBig> 트레이너 스케줄 및 일정 관리 </StyledTextBig>
      </TrRow>
      <TrRow onClick={() => navigate("trainer_ptudent_list")}>
        <StyledTextBig> 내 회원 리스트 </StyledTextBig>
      </TrRow>
    </TrainerContainer>
  );
}


export default TrainerButtons;
