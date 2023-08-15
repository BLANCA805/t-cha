import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

import { api } from "./common-data";
import { AppDispatch, type RootState } from "src/redux/store";
import { deleteProfile, logIn, logOut, postProfile } from "src/redux/slicers";

import Auth from "@shared/auth";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import Button from "@mui/material/Button";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Fab from "@mui/material/Fab";

import HomeRoundedIcon from '@mui/icons-material/HomeRounded';
import StarRoundedIcon from '@mui/icons-material/StarRounded';

import styled from "styled-components";


const BottomBarContainer = styled.div`
  display:flex;
  flex-direction: row;
  position: fixed;
  justify-content: center;
  bottom:0;
  left:0;
  background-color: ${({ theme }) => theme.color.light};
  width:100%;
  height:6rem;
  box-shadow: 0px -4px 10px rgba(0, 0, 0, 0.03);
  z-index:1;
`;

const ButtomBarContents = styled.div`
 display:flex;
 flex-direction:column;
 justify-content:center;
 align-items: center;
 height:100%;
 width:6rem;
 /* background-color: pink; */
 margin: 0rem 0.2rem;
`

function MobileBottomBar() {
  

  const navigate = useNavigate();


  function list() {
    if (user.token) {
      return (
        <Box
          sx={{ auto: 250 }}
          role="presentation"
        >
          <List>
            {[
              ["profile", "마이페이지"],
              ["", "home"],
              ["profile/schedule", "내 캘린더"],
              ["profile/chat", "채팅 목록"],
              ["trainer", "트레이너"],
              ["customer_center", "고객센터"],
            ].map((data, index) => (
              <ListItem key={index} disablePadding>
                <ListItemButton>
                  <Link to={data[0]}>
                    <ListItemText primary={data[1]} />
                  </Link>
                </ListItemButton>
              </ListItem>
            ))}
            <button onClick={LogOut}>로그아웃</button>
          </List>
          <Divider />
        </Box>
      );
    } else {
      return (
        <Box
          sx={{ auto: 250 }}
          role="presentation"
        >
          {!user.token && <button onClick={tester}>테스트 로그인</button>}
          <List>
            <Button onClick={handleAuthOpen}>로그인</Button>
            {[
              ["", "홈"],
              ["trainer", "트레이너"],
              ["customer_center", "고객센터"],
            ].map((data, index) => (
              <ListItem key={index} disablePadding>
                <ListItemButton>
                  <Link to={data[0]}>
                    <ListItemText primary={data[1]} />
                  </Link>
                </ListItemButton>
              </ListItem>
            ))}
          </List>
          <Divider />
        </Box>
      );
    }
  }

  const [authOpen, setAuthOpen] = useState(false);

  const handleAuthOpen = () => {
    setAuthOpen(true);
  };

  const handleAuthClose = () => {
    setAuthOpen(false);
  };

  const user = useSelector((state: RootState) => state.auth);
  const profile = useSelector((state: RootState) => state.profile);

  const dispatch = useDispatch<AppDispatch>();

  const LogOut = () => {
    dispatch(logOut());
    dispatch(deleteProfile());
    navigate("/");
  };

  const tester = () => {
    axios
      .post(`${api}/users?email=tester@gmail.com`)
      .then((response) => {
        const token = response.data.id;
        dispatch(
          logIn({
            token: response.data.id,
          })
        );
        return axios.post(`${api}/userProfiles`, {
          userId: token,
          name: token.slice(0, 3),
          profileImage: "",
        });
      })
      .then((response) => {
        if (response.data) {
          dispatch(
            postProfile({ name: response.data.name, id: response.data.id })
          );
          console.log(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <BottomBarContainer>
      {/* <React.Fragment> */}
        {/* {list()} */}
      {/* </React.Fragment> */}
      <ButtomBarContents onClick={() => navigate("profile/bookmarked_trainers")}>
        <StarRoundedIcon style={{fontSize:"4rem"}}/>
        즐겨찾기
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("trainer")}>
        <StarRoundedIcon style={{fontSize:"4rem"}}/>
        트레이너목록 
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("")}>
        <HomeRoundedIcon style={{fontSize:"4rem"}}/>
        홈으로
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("profile/schedule")}>
        <StarRoundedIcon style={{fontSize:"4rem"}}/>
        스케줄
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("profile")}>
        <StarRoundedIcon style={{fontSize:"4rem"}}/>
        마이페이지
      </ButtomBarContents >
      <Auth open={authOpen} onClose={handleAuthClose} />
    </BottomBarContainer>
  );
}

export default MobileBottomBar;
