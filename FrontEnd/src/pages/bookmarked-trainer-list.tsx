import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import axios from "axios";
import Pagination from "@mui/material/Pagination";

import { api } from "@shared/common-data";

import { BookmarkedTrainerData } from "src/interface";
import BookmarkedTrainerListItem from "src/containers/bookmarked-trainer/bookmarked-trainer-list-item";

import { TitleWrapper, PageTitleText,SmallPageTitleText } from "@shared/page-title";

import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 98%;
  height: 100vh;
  margin: 2% 3%;
  @media (max-width: 767px) {
    margin: 3%;
  }
`;

const TextWrapper = styled.div`
  display: flex;
  align-items: center;
  width:100%;
  justify-content: space-between;
`

const ContentsWrapper = styled.div`
  width: 100%;
  min-height:65%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: start;
  align-content: start;
  @media (max-width: 767px) {
   flex-direction:column; 
   flex-wrap: nowrap;
   min-height:70%;
  }
`;

const PaginationWrapper = styled.div`
  display: flex;
  justify-content: center;
`;

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
        <TextWrapper>
          <PageTitleText>즐겨찾기한 트레이너</PageTitleText>
          <SmallPageTitleText style={{marginRight:"5%"}}>총 {items?.pageInfo.totalElements}명</SmallPageTitleText>
        </TextWrapper>
      </TitleWrapper>

      <ContentsWrapper>
        {items?.data[0] ? (
          items.data.map((item, index) => (
            <BookmarkedTrainerListItem key={index} data={item} />
          ))
        ) : (
          <div style={{ display: "flex", justifyContent: "center" }}>
            <p>즐겨찾기 한 트레이너가 없습니다</p>
          </div>
        )}
      </ContentsWrapper>
      <PaginationWrapper>
        <Pagination
          count={items?.pageInfo.totalPages}
          page={page}
          onChange={handleChangePage}
          color="standard"
        />
      </PaginationWrapper>
    </Wrapper>
  );
}

export default BookmarkedTrainerList;
