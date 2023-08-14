import React from "react";

import { TrainerDetailData } from "src/interface";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3% 0%;
`;

const ContainerSet = styled.div`
  display: flex;
  flex-direction: column;
  height: 50rem;
  background-color: #f0f0f0;
`;

const ImageSet = styled.div`
  display: flex;
`;

const Image = styled.img`
  width: 15rem;
  height: 15rem;
`;

interface TrainerDetailDataProps {
  data: TrainerDetailData;
}

const TrainerDetail: React.FC<TrainerDetailDataProps> = ({ data }) => {
  console.log(data);
  return (
    <Wrapper>
      <ContainerSet>
        <h1>{data.title}</h1>
        <ImageSet>
          {data.images.map((image) => (
            <Image src={image} alt="" />
          ))}
        </ImageSet>
        <h4>{data.content}</h4>
      </ContainerSet>
    </Wrapper>
  );
};

export default TrainerDetail;
