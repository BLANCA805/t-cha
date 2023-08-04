import React from "react";

import { TrainerDetailData } from "src/interface";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3% 0%;
`;

const ContainerSet= styled.div`
  display:flex;
  height:50rem;
  background-color: #f0f0f0;  
`;

interface TrainerDetailDataProps {
  data: TrainerDetailData;
}

const TrainerDetail: React.FC<TrainerDetailDataProps> = ({ data }) => {
  console.log(data);
  return (
    <Wrapper>
      <ContainerSet>
        {data.content}
      </ContainerSet>
    </Wrapper>


  );
};

export default TrainerDetail;
