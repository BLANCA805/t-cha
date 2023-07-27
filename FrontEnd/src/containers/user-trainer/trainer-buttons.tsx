import styled from "styled-components";
import { Link } from "react-router-dom";

const ContainerSet = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-bottom: 3%;
`;

const TrainerContainer = styled(ContainerSet)`
  display: flex;
  flex-direction: column;
  height: 20rem;
  /* display:none; */
`;

const TrRow = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 4;
  width: 100%;
  background-color: white;
  border-radius: 10px;
  margin-top: 0.5%;
  margin-bottom: 0.5%;
`;

function TrainerButtons() {
  return (
    <TrainerContainer>
      <TrRow>
        <Link to="">트레이너 정보 수정 (트레이너 상세 페이지)</Link>
      </TrRow>
      <TrRow>
        <Link to="">트레이너 스케줄 관리, 일정</Link>
      </TrRow>
      <TrRow>
        <Link to="">내 회원 리스트</Link>
      </TrRow>
    </TrainerContainer>
  );
}

export default TrainerButtons;
