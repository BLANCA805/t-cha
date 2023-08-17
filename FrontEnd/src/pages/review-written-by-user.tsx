// import { useEffect, useState } from "react";
// import axios from "axios";
import { TitleWrapper, PageTitleText } from "@shared/page-title";
import styled from "styled-components";
import UserReviewList from "src/containers/user-review/user-review-list";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh;
  margin: 3%;
`;

const ContentsWrapper = styled.div``;

function ReviewWrittenByUser() {
  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>내가 작성한 리뷰</PageTitleText>
      </TitleWrapper>
      <ContentsWrapper>
        <UserReviewList />
      </ContentsWrapper>
    </Wrapper>
  );
}

export default ReviewWrittenByUser;
