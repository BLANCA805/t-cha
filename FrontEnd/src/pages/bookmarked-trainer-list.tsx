import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import axios from "axios";
import Pagination from "@mui/material/Pagination";

import { api } from "@shared/common-data";

import { BookmarkedTrainerData } from "src/interface";
import BookmarkedTrainerListItem from "src/containers/bookmarked-trainer/bookmarked-trainer-list-item";

import { TitleWrapper, PageTitleText } from "@shared/page-title";

import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width:100%;
  height: 100vh;
  margin: 3%;
`;

const ContentsWrapper = styled.div``;

function BookmarkedTrainerList() {
  const user = useSelector((state: RootState) => state.profile);

  const [items, setItems] = useState<BookmarkedTrainerData>();
  const [page, setPage] = useState(1);
  const handleChangePage = (
    event: React.ChangeEvent<unknown>,
    value: number
  ) => {
    setPage(value);
  };

  useEffect(() => {
    axios
      .get(`${api}/bookmarks/${user.profileId}?page=${page}&size=5`)
      .then((response) => {
        setItems(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [user.profileId, page]);

  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>즐겨찾기한 트레이너</PageTitleText>
      </TitleWrapper>

      <ContentsWrapper>
        {items &&
          items.data.map((item, index) => (
            <BookmarkedTrainerListItem key={index} data={item} />
          ))}
      </ContentsWrapper>
      <Pagination
        count={items?.pageInfo.totalPages}
        page={page}
        onChange={handleChangePage}
        color="standard"
        style={{ alignSelf: "center" }}
      />
    </Wrapper>
  );
}

export default BookmarkedTrainerList;
