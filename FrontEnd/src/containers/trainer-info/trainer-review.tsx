import React from "react";

import { TrainerReviewData } from "src/interface";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
`;

const TrainerReview: React.FC<TrainerReviewData> = ({ data }) => {
  console.log(data);
  return <Wrapper>트레이너 리뷰 페이지 입니다.</Wrapper>;
};

export default TrainerReview;
