import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

import styled from "styled-components";

import Carousel from "react-material-ui-carousel";
import { Paper } from "@mui/material";
import useAxios from "src/hooks/use-axios";

const Container = styled.div`
  background-color: white;
  padding: 2%;
  border-radius: 10px;
  margin-top: 3%;
  margin-bottom: 3%;
`;

const Wrapper = styled.div`
  display: flex;
`;

const Column = styled.div`
  width: 50%;
`;

const ImgTag = styled.img`
  width: 100%;
  height: 100%;
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

  return (
    <Container>
      <h3>딱 필요한 트레이너를 찾는 가장 현명한 방법</h3>
      <Wrapper>
        <Column style={{ display: "flex", alignItems: "end" }}>
          <Link to="trainer">모든 트레이너 보러가기</Link>
        </Column>
        <Column>
          <Carousel>
            {items.map((item: { download_url: string }, index: number) => (
              <div key={index}>
                <Paper style={{ width: "100%", height: "30rem" }}>
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
