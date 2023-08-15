// import { useEffect, useState } from "react";
// import axios from "axios";
import { TitleWrapper, PageTitleText } from "@shared/page-title";
import styled from "styled-components";
import BookedTrainerList from "src/containers/bookmarked-trainer/bookmarked-trainer-list";

const Wrapper=styled.div`
  display:flex;
  flex-direction: column;
  margin: 3%;
  height:100vh;
`;

 

const ContentsWrapper=styled.div`
  
`;

function BookMarkedTrainerList() {
  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>즐겨찾기한 트레이너</PageTitleText>
      </TitleWrapper>

      <ContentsWrapper>
        <BookedTrainerList />
      </ContentsWrapper>
    </Wrapper>
  );
}

export default BookMarkedTrainerList;
