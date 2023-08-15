import { useEffect, useState } from "react";
import axios from "axios";

import Carousel from "react-material-ui-carousel";
// import { Paper } from "@mui/material";

import styled from "styled-components";

const Container = styled.div`
  margin-top: 3%;
  margin-bottom: 3%;
`;

const ImgTag = styled.img`
  width: 100%;
  height: 20rem;
  object-fit: cover;
`;

function Slide() {
  const [items, setItems] = useState<Array<any>>([]);

  useEffect(() => {
    axios
      .get("https://picsum.photos/v2/list?page=2&limit=5")
      .then((response) => {
        setItems(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  return (
    <Container>
      <Carousel>
        {items.map((item) => (
          <div key={item.id}>
            <ImgTag src={item.download_url} alt={item.url} />
          </div>
        ))}
      </Carousel>
    </Container>
  );
}

export default Slide;
