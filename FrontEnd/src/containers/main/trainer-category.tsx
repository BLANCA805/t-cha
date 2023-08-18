import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

import styled from "styled-components";

import Carousel from "react-material-ui-carousel";
import { Paper } from "@mui/material";
import ArrowForwardIosRoundedIcon from "@mui/icons-material/ArrowForwardIosRounded";
import useAxios from "src/hooks/use-axios";

const Container = styled.div`
  display: flex;
  background-color: white;
  padding: 2%;
  height: 24rem;
  border-radius: 10px;
  margin-top: 3%;
  margin-bottom: 3%;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    height: 10rem;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  }
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
`;

const Column = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 1% 3% 0% 3%;
  width: 50%;
`;
const ColumnSB = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 1% 2%;
  width: 50%;
  @media (max-width: 767px) {
    padding: 1% 0%;
    margin: 0% 0% 0% 3%;
  }
`;
const ImageWithText = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`;

const ImgTag = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 7px;
  object-fit: cover;
`;

const ImgText = styled.h5`
  position: absolute;
  bottom: 7%;
  left: 5%;
  color: white;
  font-size: 1.5rem;
  margin: 0% 0%;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 1%;
  border-radius: 5px;
  @media (max-width: 767px) {
    font-size: 0.5rem;
  }
`;

const TextTitleWrapper = styled.div`
  margin-top: 3%;
  @media (max-width: 767px) {
    margin-top: 5%;
  }
`;
const StyledTextTitle = styled.h4`
  font-size: 2.4rem;
  margin: 1% 0% 4% 0%;
  @media (max-width: 767px) {
    margin: 5% 0% 3% 0%;
    font-size: 0.95rem;
  }
`;
const GoToTrainer = styled.h6`
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
  margin-bottom: 7% !important;
  font-size: 1.7rem !important;
  cursor: pointer;
  transition: transform 0.3s ease;
  &:hover {
    transform: scale(1.02);
  }

  @media (max-width: 767px) {
    font-size: 0.8rem !important;
    margin-bottom: 10% !important;
  }
`;

function TrainerCategory() {
  const items = [
    {
      image:
        "https://blanca05.s3.ap-northeast-2.amazonaws.com/image/kaushal-mishra-p76UivR30oo-unsplash.jpg",
      text: "최근에 등록한 트레이너",
      sort: "",
    },
    {
      image:
        "https://blanca05.s3.ap-northeast-2.amazonaws.com/image/nasa-OVO8nK-7Rfs-unsplash.jpg",
      text: "별점이 많은 트레이너",
      sort: "/sorted-by-star",
    },
    {
      image:
        "https://blanca05.s3.ap-northeast-2.amazonaws.com/image/markus-winkler--fRAIQHKcc0-unsplash.jpg",
      text: "리뷰가 많은 트레이너",
      sort: "/sorted-by-review",
    },
    {
      image:
        "https://blanca05.s3.ap-northeast-2.amazonaws.com/image/bernd-klutsch-nE2HV5AUXFo-unsplash.jpg",
      text: "즐겨찾기가 많은 트레이너",
      sort: "/sorted-by-bookmark",
    },
    {
      image:
        "https://blanca05.s3.ap-northeast-2.amazonaws.com/image/timon-studler-ABGaVhJxwDQ-unsplash.jpg",
      text: "PT를 많이 진행한 트레이너",
      sort: "/sorted-by-pt",
    },
  ];

  const navigate = useNavigate();

  const goToLink = () => {
    navigate("trainer");
  };

  return (
    <Container>
      <Wrapper>
        <ColumnSB>
          <TextTitleWrapper>
            <StyledTextTitle>딱 맞는 트레이너를 찾는</StyledTextTitle>
            <StyledTextTitle>가장 현명한 방법</StyledTextTitle>
          </TextTitleWrapper>
          <div>
            <GoToTrainer onClick={goToLink}>
              모든 트레이너 보러가기
              <ArrowForwardIosRoundedIcon
                style={{ fontSize: "1.2rem", marginLeft: "3%" }}
              />
            </GoToTrainer>
          </div>
        </ColumnSB>
        <Column>
          <Carousel>
            {items.map((item, index: number) => (
              <div key={index}>
                <Paper
                  style={{
                    width: "100%",
                    maxHeight: "21rem",
                    aspectRatio: "3/2",
                  }}
                >
                  <ImageWithText
                    onClick={() => navigate("/trainer", { state: item.sort })}
                  >
                    <ImgTag src={item.image} alt="" />
                    <ImgText>{item.text}</ImgText>
                  </ImageWithText>
                </Paper>
              </div>
            ))}
          </Carousel>
        </Column>
      </Wrapper>
    </Container>
  );
}

export default TrainerCategory;
