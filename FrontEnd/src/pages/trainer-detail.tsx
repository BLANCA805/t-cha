import { Outlet } from "react-router-dom";

import ToggleButtons from "@shared/toggle-button";

import styled from "styled-components";

const Container = styled.div``;

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.light};
`;

function TrainerDetail() {
  return (
    <Container>
      <Wrapper>
        <h3>트레이너 상세 페이지 입니다.</h3>
      </Wrapper>
      <Wrapper>
        <ToggleButtons
          tabs={[
            { text: "트레이너 상세 정보", path: "info" },
            { text: "트레이너 리뷰", path: "review" },
          ]}
          width="100%"
        />
        <Outlet />
      </Wrapper>
    </Container>
  );
}

export default TrainerDetail;
