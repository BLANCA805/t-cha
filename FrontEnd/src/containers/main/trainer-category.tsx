import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

import styled from "styled-components";

import Carousel from "react-material-ui-carousel";
import { Paper } from "@mui/material";
import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import useAxios from "src/hooks/use-axios";

const Container = styled.div`
  display:flex;
  background-color: white;
  padding: 2%;
  height:24rem;
  border-radius: 10px;
  margin-top: 3%;
  margin-bottom: 3%;
  @media (max-width: 767px) {
    height: 10rem;
  }

`;

const Wrapper = styled.div`
  display: flex;
  flex-direction:row;
  width:100%;
`;


const Column = styled.div`
  display:flex;
  flex-direction:column;
  justify-content:center;
  padding:1% 3% 0% 3%;
  width: 50%;
`;
const ColumnSB = styled.div`
  display:flex;
  flex-direction:column;
  justify-content:space-between;
  padding:1% 2%;
  width: 50%;
  @media (max-width: 767px) {
    padding:1% 0%;
    margin:0% 0% 0% 3%;
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
  font-size:1.5rem; 
  margin: 0% 0%;
  background-color: rgba(0, 0, 0, 0.5); 
  padding: 1%;
  border-radius: 5px;
  @media (max-width: 767px) {
    font-size:0.5rem;
  }
`;

const TextTitleWrapper = styled.div`
  margin-top:3%;
  @media (max-width: 767px) {
    margin-top: 5%;
  }
`
const StyledTextTitle = styled.h5`
  font-size:2.4rem;
  margin:1% 0% 4% 0%;
  color:black;
  @media (max-width: 767px) {
    margin:5% 0% 3% 0%;
    font-size:0.95rem;
  }
`
const GoToTrainer = styled.h6`
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
  margin-bottom: 7% !important;
  font-size:1.7rem !important;
  cursor:pointer;
  transition: transform 0.3s ease;
  &:hover {
    transform: scale(1.02); 
  }

  @media (max-width: 767px) {
    font-size:0.8rem !important;
    margin-bottom: 10% !important;
  }
  
  
`


function TrainerCategory() {
  const [items, setItems] = useState<Array<any>>([]);
  
  useEffect(() => {
    axios
      .get("https://picsum.photos/v2/list?page=12&limit=5")
      .then((response) => {
        setItems(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const navigate = useNavigate();
  const goToLink = () =>{
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
          <div >
            <GoToTrainer onClick={goToLink}>
              모든 트레이너 보러가기
              <ArrowForwardIosRoundedIcon style={{fontSize:"1.2rem",marginLeft:"3%"}} /> 
            </GoToTrainer>
          </div>
        </ColumnSB>
        <Column>
          <Carousel>
            {items.map((item: { download_url: string }, index: number) => (
              <div key={index}>
                <Paper style={{ width: "100%", maxHeight:"21rem", aspectRatio:"3/2" }}>
                  <ImageWithText>
                    <ImgTag src={item.download_url} alt="" />
                    {/* <ImgText>지금 바로 운동 가능한 트레이너를 찾는다면?</ImgText> */}
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
