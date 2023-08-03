import React from "react";
import { TrainerDetailData } from "src/interface";
import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
`;

interface TrainerDetailDataProps {
  data: TrainerDetailData;
}

const TrainerDetail: React.FC<TrainerDetailDataProps> = ({ data }) => {
  console.log(data);
  return <Wrapper>트레이너 상세 정보 페이지 입니다.</Wrapper>;
};

export default TrainerDetail;
