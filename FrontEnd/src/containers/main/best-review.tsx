import { api } from "@shared/common-data";
import axios from "axios";
import { useEffect, useState } from "react";
import { ReviewData } from "src/interface";

import styled from "styled-components";
import Rating from "@mui/material/Rating";

const Container = styled.div`
  background-color: white;
  padding: 2%;
  border-radius: 10px;
  /* margin-top: 3%; */
  margin-bottom: 3%;
`;

const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

const ContainerTitle = styled.h4`
  margin: 1% 3% 3% 2%  !important;
  font-size: 2.3rem;
  @media (max-width: 767px) {
    margin: 3% 2.5% !important;
    font-size: 1.2rem !important;
  }
`;
const ContentsWrapper = styled.div`
  margin-bottom: 1.5%;
`;
const Context = styled.h6`
  display: flex;
  align-items: center;
  margin: 0% 2% !important;

  @media (max-width: 767px) {
    margin: 0% 2% !important;
    font-size: 0.7rem !important;
  }
`;
const StyledRating = styled(Rating)`
  margin: 2.5% !important;
  @media (max-width: 767px) {
    font-size: 0.8rem !important;
    margin-top: 2.3% !important;
    margin-bottom: 2.5% !important;
  }
`;

function BestReview() {
  const [bestReviewItems, setBestReviewItems] = useState<ReviewData>();

  useEffect(() => {
    axios.get(`${api}/reviews?page=1&size=5`).then((response) => {
      setBestReviewItems(response.data);
    });
  });

  return (
    <Container>
      <ContainerTitle>서비스 후기</ContainerTitle>
      <ContentsWrapper>
        {bestReviewItems?.data.map((item, index) => (
          <Wrapper key={index}>
            <Context>{item.content}</Context>
            <StyledRating value={item.star} precision={0.5} readOnly />
          </Wrapper>
        ))}
      </ContentsWrapper>
    </Container>
  );
}

export default BestReview;
