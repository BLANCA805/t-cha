import { useEffect, useState } from "react";
import axios from "axios";

import { api } from "@shared/common-data";

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
  const [items, setItems] = useState([]);

  useEffect(() => {
    axios
      .get(`${api}/trainers`)
      .then((response) => {
        setItems(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  // [
  //   {
  //     id: 1,
  //     name: "이채림",
  //     keywordTags: ["근력", "유산소", "필라테스"],
  //     profileImg: "",
  //   },
  //   {
  //     id: 2,
  //     name: "하정호",
  //     keywordTags: ["근력운동", "체력증진", "저녁반"],
  //     profileImg: "",
  //   },
  //   {
  //     id: 3,
  //     name: "변정원",
  //     keywordTags: ["근력", "자세교정", "새벽가능"],
  //     profileImg: "",
  //   },
  // ];
  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>즐겨찾기한 트레이너</PageTitleText>
      </TitleWrapper>

      <ContentsWrapper>
        {items.map((item) => (
          <BookmarkedTrainerListItem data={item} />
        ))}
      </ContentsWrapper>
    </Wrapper>
  );
}

export default BookmarkedTrainerList;
