import { useState, useEffect } from "react";
import axios from "axios";
import UserReviewListItem from "./user-review-list-item";

import styled from "styled-components";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { api } from "@shared/common-data";
import { ReviewData } from "src/interface";

const Wrapper = styled.div``;

function UserReviewList() {
  const user = useSelector((state: RootState) => state.profile);

  const [items, setItems] = useState<ReviewData>();

  useEffect(() => {
    axios
      .get(`${api}/reviews/user/${user.profileId}`)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  });

  return (
    <Wrapper>
      {items?.data.map((item, index) => (
        <UserReviewListItem key={index} data={item} />
      ))}
    </Wrapper>
  );
}

export default UserReviewList;
