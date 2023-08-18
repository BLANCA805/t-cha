import React from "react";

import { TrainerDetailData } from "src/interface";

import styled from "styled-components";

const Wrapper = styled.div`
  max-width: 100%;
  height: 85vh;
  padding: 3% 4%;
  @media (max-width: 767px) {
    height:56vh;
  }
`;

const ContainerSet = styled.div`
  /* display: flex; */
  width: 100%;
  /* background-color: #f0f0f0; */
`;

const StyledTextBig = styled.h5`
  margin: 3% 0%;
  font-size: 3.4rem;
  @media (max-width: 767px) {
    font-size: 1.2rem;
  }
`;

const StyledTextSmall = styled.h6`
  margin: 0.3% 0%;
  font-size: 1.5rem;
  @media (max-width: 767px) {
    font-size: 0.6rem;
  }
`;

const Image = styled.img`
  width: 12rem;
  height: 12rem;
`;

interface TrainerDetailDataProps {
  data: TrainerDetailData;
}

const TrainerDetail: React.FC<TrainerDetailDataProps> = ({ data }) => {
  console.log(data);
  return (
    <Wrapper>
      <ContainerSet>
        <StyledTextBig>{data.title}</StyledTextBig>
        {data.images.map((image, index) => (
          <Image src={image} key={index} alt="" style={{margin:"2%"}}/>
        ))}
        <StyledTextSmall>{data.content}</StyledTextSmall>
      </ContainerSet>
    </Wrapper>
  );
};

export default TrainerDetail;
