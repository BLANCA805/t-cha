import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

import { api } from "./common-data";
import { AppDispatch, type RootState } from "src/redux/store";
import { deleteProfile, logOut, postProfile } from "src/redux/slicers";

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

import styled from "styled-components";

const SideBarContainer = styled.div`
  display:flex;
  flex-direction: column;
  position: sticky;
  top:0;
  left:0;
  background-color: ${({ theme }) => theme.color.light};
  opacity: 75%;
  width:16rem;
  height:100vh;
`;

function DesktopSideBar() {
  const [open, setOpen] = useState(false);

  const navigate = useNavigate();

  const toggleDrawer =
    (open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
      if (
        event.type === "keydown" &&
        ((event as React.KeyboardEvent).key === "Tab" ||
          (event as React.KeyboardEvent).key === "Shift")
      ) {
        return;
      }

      setOpen(open);
    };

  function list() {
    if (user.token) {
      return (
          <List>
            {!profile.profileId && (
              <button onClick={test}>프로필 생성하기</button>
            )}
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
      );
    } else {
      return (

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

      );
    }
  }

  const [authOpen, setAuthOpen] = useState(false);

  const handleAuthOpen = () => {
    toggleDrawer(false);
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

  const test = () => {
    const token = user.token;
    axios
      .post(`${api}/userProfiles`, {
        userId: token,
        name: token.slice(0, 3),
        profileImage: "이미지",
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
    <SideBarContainer>
      <React.Fragment>
        {list()}
      </React.Fragment>
      <Auth open={authOpen} onClose={handleAuthClose} />
    </SideBarContainer>
  );
}

export default DesktopSideBar;