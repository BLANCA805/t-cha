import { useState } from "react";
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

import HomeRoundedIcon from "@mui/icons-material/HomeRounded";
import StarRoundedIcon from "@mui/icons-material/StarRounded";

import styled from "styled-components";

const BottomBarContainer = styled.div`
  display: flex;
  flex-direction: row;
  position: fixed;
  justify-content: center;
  bottom: 0;
  left: 0;
  background-color: ${({ theme }) => theme.color.light};
  width: 100%;
  height: 6rem;
  box-shadow: 0px -4px 10px rgba(0, 0, 0, 0.03);
  z-index: 1;
`;

const ButtomBarContents = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 6rem;
  /* background-color: pink; */
  margin: 0rem 0.2rem;
`;

function MobileBottomBar() {
  const navigate = useNavigate();

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

  return (
    <BottomBarContainer>
      {/* <React.Fragment> */}
      {/* {list()} */}
      {/* </React.Fragment> */}
      <ButtomBarContents
        onClick={() => navigate("profile/bookmarked_trainers")}
      >
        <StarRoundedIcon style={{ fontSize: "4rem" }} />
        즐겨찾기
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("trainer")}>
        <StarRoundedIcon style={{ fontSize: "4rem" }} />
        트레이너목록
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("")}>
        <HomeRoundedIcon style={{ fontSize: "4rem" }} />
        홈으로
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("profile/schedule")}>
        <StarRoundedIcon style={{ fontSize: "4rem" }} />
        스케줄
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("profile")}>
        <StarRoundedIcon style={{ fontSize: "4rem" }} />
        마이페이지
      </ButtomBarContents>
      <Auth open={authOpen} onClose={handleAuthClose} />
    </BottomBarContainer>
  );
}

export default MobileBottomBar;
