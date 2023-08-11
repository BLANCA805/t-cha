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
  height:15rem;
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

const GoToTrainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 12%;
`

const Column = styled.div`
  display:flex;
  flex-direction:column;
  justify-content:center;
  padding:0% 3%;
  width: 50%;
`;
const ColumnSB = styled.div`
  display:flex;
  flex-direction:column;
  justify-content:space-between;
  padding:0% 3%;
  width: 50%;
`;

const ImgTag = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 7px;
  object-fit: cover;
`;

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
          <div style={{marginTop:"10%"}}>
            <h5 style={{fontSize:"1rem", margin:"0% 0% 3% 0%"}}>딱 맞는 트레이너를 찾는</h5>
            <h5 style={{fontSize:"1rem", margin:"0%"}}>가장 현명한 방법</h5>
          </div>
          <div >
            <GoToTrainer onClick={goToLink}>
              <h6 style={{fontSize:"0.8rem",margin:"0%"}}>모든 트레이너 보러가기</h6>
              <ArrowForwardIosRoundedIcon style={{fontSize:"1.2rem",marginLeft:"3%"}} /> 
            </GoToTrainer>
          </div>
        </ColumnSB>
        <Column>
          <Carousel>
            {items.map((item: { download_url: string }, index: number) => (
              <div key={index}>
                <Paper style={{ width: "100%", aspectRatio:"3/2" }}>
                  <ImgTag src={item.download_url} alt="" />
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
