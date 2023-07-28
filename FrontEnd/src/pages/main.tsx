import Slide from "@shared/slide";
import RecommendTrainer from "@main/recommend-trainer";
import TrainerCategory from "@main/trainer-category";
import BestReview from "@main/best-review";

import styled from "styled-components";

const Container = styled.div`
  margin: 3%;
`;

function Main() {
  return (
    <Container>
      <Slide />
      <RecommendTrainer />
      <TrainerCategory />
      <BestReview />
    </Container>
  );
}

export default Main;
