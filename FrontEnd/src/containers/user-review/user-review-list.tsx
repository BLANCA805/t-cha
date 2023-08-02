import { useState, useEffect } from "react";
import axios from "axios";
import UserReviewListItem from "./user-review-list-item";

import styled from "styled-components";

const Wrapper = styled.div`
  
`;



function UserReviewList(){
  
  const items = [
    {
      id: 1,
      username: "하정호",
      date: "2023-03-03",
      rate:"★ 4.5",
      contents: "수업이 알차고 재밌습니다 ~",
    },
    {
      id: 2,
      username: "변정원",
      date: "2023-03-03",
      rate:"★ 5.0",
      contents: "수업이 알차요",
    },
  ]


  return (

    <Wrapper>
      {items.map((item) => ( 
        <UserReviewListItem data= {item}/>
      ))}
    </Wrapper>

  );
}

export default UserReviewList;