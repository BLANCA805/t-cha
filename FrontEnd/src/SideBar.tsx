import styled from "styled-components";

import { Link } from "react-router-dom";
import React, { useState } from "react";
import Auth from "./screen/auth/Auth";
import { Modal } from "@mui/material";

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
  return (
    <Wrapper>
      <h3>사이드바도 어디에서든 보여야합니다.</h3>
      <ul>
        <li>
          <Link to="#" onClick={handleAuthOpen}>로그인</Link>
        </li>
        <li>
          <Link to="">홈으로</Link>
        </li>
        <li>
          <Link to="userid/calendar">내 캘린더</Link>
        </li>
        <li>
          <Link to="chat">채팅 목록 보기</Link>
        </li>
        <li>
          <Link to="pt">트레이너들 보러가기</Link>
        </li>
        <li>
          <Link to="customer-center">고객센터</Link>
        </li>
      </ul>
      <Modal open={authOpen} onClose={handleAuthClose}>
        <Auth open={authOpen} onClose={handleAuthClose} />
      </Modal>
    </Wrapper>
  );
}

export default SideBar;
