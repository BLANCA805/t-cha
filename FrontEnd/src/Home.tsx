import Slide from "./component/Slide";
import Recommend from "./component/Recommend";
import TrainerFilter from "./component/TrainerFilter";
import BestReview from "./component/BestReview";

import styled from "styled-components";

const Container = styled.div`
  margin: 3%;
`;

function Home() {
  return (
    <Container>
      <Slide />
      <Recommend />
      <TrainerFilter />
      <BestReview />
    </Container>
  );
}

export default Home;
