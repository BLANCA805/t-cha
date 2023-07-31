import { useState } from "react";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

import { AppDispatch, type RootState } from "src/redux/store";
import { logOut } from "src/redux/slicers";

import Auth from "@shared/auth";

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

  const user = useSelector((state: RootState) => state.auth);

  const dispatch = useDispatch<AppDispatch>();

  const LogOut = () => {
    dispatch(logOut());
  };

  const test = () => {
    const token = user.token;
    axios
      .post(`http://70.12.245.39:8080/userProfiles/${token}`, {
        name: "임병국",
        profileImage: "이미지주소",
      })
      .then((response) => {
        if (response.data) {
          console.log(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  if (user.isLogined) {
    return (
      <Wrapper>
        <ul>
          <li>
            <button onClick={test}>test</button>
          </li>
          <br />
          <li>{user.isLogined && <Link to="profile">마이페이지</Link>}</li>
          <br />
          <li>
            <Link to="">홈</Link>
          </li>
          <br />
          <li>
            <Link to="profile/schedule">내 캘린더</Link>
          </li>
          <br />
          <li>
            <Link to="profile/chat">채팅 목록</Link>
          </li>
          <br />
          <li>
            <Link to="trainer">트레이너</Link>
          </li>
          <br />
          <li>
            <Link to="customer-center">고객센터</Link>
          </li>
          <br />
          <li>
            <button onClick={LogOut}>로그아웃</button>
          </li>
        </ul>
        <Auth open={authOpen} onClose={handleAuthClose} />
      </Wrapper>
    );
  } else {
    return (
      <Wrapper>
        <ul>
          <li>
            <Link to="#" onClick={handleAuthOpen}>
              로그인
            </Link>
          </li>
          <br />
          <li>
            <Link to="">홈</Link>
          </li>
          <br />
          <li>
            <Link to="trainer">트레이너</Link>
          </li>
          <br />
          <li>
            <Link to="customer-center">고객센터</Link>
          </li>
        </ul>
        <Auth open={authOpen} onClose={handleAuthClose} />
      </Wrapper>
    );
  }
}

export default SideBar;
