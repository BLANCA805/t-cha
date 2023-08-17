import { useState, useEffect } from "react";
import axios from "axios";
import UserReviewListItem from "./user-review-list-item";

import styled from "styled-components";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { api } from "@shared/common-data";
import { ReviewData } from "src/interface";
import { Pagination } from "@mui/material";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

function UserReviewList() {
  const user = useSelector((state: RootState) => state.profile);

  const [items, setItems] = useState<ReviewData>();
  const [page, setPage] = useState(1);

  const handleChangePage = (
    event: React.ChangeEvent<unknown>,
    value: number
  ) => {
    setPage(value);
  };

  useEffect(() => {
    axios
      .get(`${api}/reviews/user/${user.profileId}?page=${page}&size=10`)
      .then((response) => {
        console.log(response.data);
        setItems(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [page, user.profileId]);

  return (
    <Wrapper>
      {items?.data[0] ? (
        items.data.map((item, index) => (
          <UserReviewListItem key={index} data={item} />
        ))
      ) : (
        <div>
          <p>아직 작성한 리뷰가 없습니다</p>
        </div>
      )}
      <div style={{ display: "flex", justifyContent: "center" }}>
        <Pagination
          count={items?.pageInfo.totalPages}
          page={page}
          onChange={handleChangePage}
          color="standard"
        />
      </div>
    </Wrapper>
  );
}

export default UserReviewList;
