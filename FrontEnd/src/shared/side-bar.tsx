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

import styled from "styled-components";

const Sticky = styled.div`
  position: fixed;
  z-index:1;
  bottom: 0;
`;

function SideBar() {
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
        <Box
          sx={{ auto: 250 }}
          role="presentation"
          onClick={toggleDrawer(false)}
          onKeyDown={toggleDrawer(false)}
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
          onClick={toggleDrawer(false)}
          onKeyDown={toggleDrawer(false)}
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
    <Sticky>
      <React.Fragment>
        <Fab onClick={toggleDrawer(true)}>Nav</Fab>
        <Drawer open={open} onClose={toggleDrawer(false)}>
          {list()}
        </Drawer>
      </React.Fragment>
      <Auth open={authOpen} onClose={handleAuthClose} />
    </Sticky>
  );
}

export default SideBar;
