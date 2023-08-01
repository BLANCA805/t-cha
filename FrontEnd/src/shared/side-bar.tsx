import React, { useState } from "react";
import { Link } from "react-router-dom";
import store from "src/store";

import Auth from "@shared/auth";

import { Modal } from "@mui/material";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 0.5%;
  padding: 3rem;
  background-color: white;
  width: 20%;
  border-radius: 10px;
`;

function SideBar() {
  const [authOpen, setAuthOpen] = useState(false);

  const handleAuthOpen = () => {
    setAuthOpen(true);
  };

  const handleAuthClose = () => {
    setAuthOpen(false);
  };

  const testClick = () => {
    console.log(store.getState());
  };

  const id = "1";
  return (
    <Wrapper>
      <h3>사이드바도 어디에서든 보여야합니다.</h3>
      <ul>
        <li>
          <Link to="#" onClick={handleAuthOpen}>
            로그인
          </Link>
        </li>
        <li>
          <Link to="">홈으로</Link>
        </li>
        <li>
          <Link to={id}>마이페이지</Link>
        </li>
        <li>
          <Link to=":user-id/schedule">내 캘린더</Link>
        </li>
        <li>
          <Link to=":user-id/chat">채팅 목록 보기</Link>
        </li>
        <li>
          <Link to="trainer">트레이너들 보러가기</Link>
        </li>
        <li>
          <Link to="customer-center">고객센터</Link>
        </li>
        <li>
          <button onClick={testClick}>test</button>
        </li>
      </ul>
      <Auth open={authOpen} onClose={handleAuthClose} />
    </Wrapper>
  );
}

export default SideBar;
