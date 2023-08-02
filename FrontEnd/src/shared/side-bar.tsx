import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

import { AppDispatch, type RootState } from "src/redux/store";
import { logOut, postProfile } from "src/redux/slicers";

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

const Sticky = styled.div`
  position: fixed;
  bottom: 0;
`;

function SideBar() {
  const [open, setOpen] = useState(false);

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
    if (user.isLogined) {
      return (
        <Box
          sx={{ auto: 250 }}
          role="presentation"
          onClick={toggleDrawer(false)}
          onKeyDown={toggleDrawer(false)}
        >
          <List>
            <button onClick={test}>test</button>
            {[
              ["profile", "마이페이지"],
              ["", "home"],
              ["profile/schedule", "내 캘린더"],
              ["profile/chat", "채팅 목록"],
              ["trainer", "트레이너"],
              ["customer-center", "고객센터"],
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
          onClick={toggleDrawer(false)}
          onKeyDown={toggleDrawer(false)}
        >
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
    toggleDrawer(false);
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
      .post(`http://i9a805.p.ssafy.io:8080/userProfiles/${token}`, {
        name: "임병국",
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
    <Sticky>
      <React.Fragment>
        <Button onClick={toggleDrawer(true)}>
          <Fab variant="extended">Nav</Fab>
        </Button>
        <Drawer open={open} onClose={toggleDrawer(false)}>
          {list()}
        </Drawer>
      </React.Fragment>
      <Auth open={authOpen} onClose={handleAuthClose} />
    </Sticky>
  );
}

export default SideBar;
