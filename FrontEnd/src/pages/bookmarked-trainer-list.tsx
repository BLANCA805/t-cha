import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import axios from "axios";

import { api } from "@shared/common-data";

import { BookmarkedTrainerData } from "src/interface";
import BookmarkedTrainerListItem from "src/containers/bookmarked-trainer/bookmarked-trainer-list-item";

import { TitleWrapper, PageTitleText } from "@shared/page-title";

import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 3%;
  height: 100vh;
`;

const ContentsWrapper = styled.div``;

function BookmarkedTrainerList() {
  const user = useSelector((state: RootState) => state.profile);

  const [items, setItems] = useState<BookmarkedTrainerData>();

  useEffect(() => {
    axios
      .get(`${api}/bookmarks/${user.profileId}?page=1&size=5`)
      .then((response) => {
        setItems(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [user.profileId]);

  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>즐겨찾기한 트레이너</PageTitleText>
      </TitleWrapper>

      <ContentsWrapper>
        {items &&
          items.data.map((item) => <BookmarkedTrainerListItem data={item} />)}
      </ContentsWrapper>
    </Wrapper>
  );
}

export default BookmarkedTrainerList;
