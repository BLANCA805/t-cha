import Slide from "@shared/slide";
import RecommendTrainer from "@main/recommend-trainer";
import TrainerCategory from "@main/trainer-category";
import BestReview from "@main/best-review";
import mainbannerdummy from "src/shared/img/mainbannerdummy.png";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;
const ContentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* align-items: center; */
  width: 95%;
  margin: 3%;
  @media (max-width: 767px) {
    width: 97%;
  }
`;
const BannerWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 95%;
  margin-top: 2%;
  height: 30rem;

  /* background-color: white; */
  border-radius: 10px;
  background-color: #125b51;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);

  @media (max-width: 767px) {
    height: 10rem;
    margin-top: 2%;
    width: 97%;
    border-radius: 5px;
  }
`;
const Banner = styled.img`
  display: flex;
  width: 90%;
  height: 100%;
  object-position: center 40%;
  object-fit: contain;
  @media (max-width: 767px) {
    height: 10rem;
    object-fit: contain;
  }
`;

function Main() {
  return (
    <Wrapper>
      <BannerWrapper>
        <Banner src={mainbannerdummy} />
      </BannerWrapper>
      {/* <Slide /> */}
      <ContentsWrapper>
        <RecommendTrainer />
        <TrainerCategory />
        <BestReview />
      </ContentsWrapper>
    </Wrapper>
  );
}

export default Main;
