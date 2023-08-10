import Slide from "@shared/slide";
import RecommendTrainer from "@main/recommend-trainer";
import TrainerCategory from "@main/trainer-category";
import BestReview from "@main/best-review";

import styled from "styled-components";

const Wrapper = styled.div`
  display:flex;
  flex-direction:column;
  align-items: center;
  width:100%;
`
const ContentsWrapper = styled.div`
  display:flex;
  flex-direction: column;
  justify-content: center;
  /* align-items: center; */
  width:90%;
  margin: 3%;
  @media (max-width: 767px) {
    width: 97%;
  }
`;

function Main() {
  return (
    <Wrapper>
      <Slide />
      <ContentsWrapper>
        <RecommendTrainer />
        <TrainerCategory />
        <BestReview />
      </ContentsWrapper>
    </Wrapper>
  );
}

export default Main;
