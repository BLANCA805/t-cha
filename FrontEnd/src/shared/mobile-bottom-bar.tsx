import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

import { api } from "./common-data";
import { AppDispatch, type RootState } from "src/redux/store";
import { deleteProfile, logOut } from "src/redux/slicers";

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

import Logo from "src/shared/icons/LOGO.png";
import HomeRoundedIcon from "@mui/icons-material/HomeRounded";
import StarRoundedIcon from "@mui/icons-material/StarRounded";
import EventNoteRoundedIcon from '@mui/icons-material/EventNoteRounded';
import PersonRoundedIcon from '@mui/icons-material/PersonRounded';
import BallotIcon from '@mui/icons-material/Ballot';
import FitnessCenterOutlinedIcon from '@mui/icons-material/FitnessCenterOutlined';
import styled from "styled-components";

const BottomBarContainer = styled.div`
  display: flex;
  flex-direction: row;
  position: fixed;
  justify-content: space-around;
  bottom: 0;
  left: 0;
  background-color: ${({ theme }) => theme.color.light};
  width: 100%;
  height: 5.5rem;
  box-shadow: 0px -4px 10px rgba(0, 0, 0, 0.03);
  z-index: 1;
`;

const ButtomBarContents = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 15%;
  color: #acacac;
  /* background-color: pink; */
  margin: 0rem 0.2rem;
`;

const ButtomBarHome = styled.div`
  position: relative;
  bottom: 7%;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 5.75rem;
  height: 5.75rem;
  aspect-ratio: 1/1;
  border-radius: 50%;
  /* background-color: pink; */
  background: linear-gradient(30deg, #0bd8ce, #2a4d43);
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.2) !important;
  overflow-y: display;
  z-index: 2;
`;
const ButtomBarIconImage = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 90%;
  aspect-ratio: 1/1;
`;
const ButtomBarHomeImage = styled(ButtomBarIconImage)`
  background-color: #fff;
  border-radius: 50%;
`;
const ButtomBarHomeImg = styled.img`
  width: 65%;
  margin-right: 5%;
  object-fit: contain;
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
      <ButtomBarContents
        onClick={() => navigate("profile/bookmarked_trainers")}
      >
        <StarRoundedIcon style={{ fontSize: "3.3rem" }} />
        즐겨찾기
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("trainer")}>
        <FitnessCenterOutlinedIcon style={{ fontSize: "3.3rem" }} />
        트레이너목록
      </ButtomBarContents>
      <ButtomBarHome onClick={() => navigate("")}>
        <ButtomBarHomeImage>
          <ButtomBarHomeImg src={Logo}></ButtomBarHomeImg>
        </ButtomBarHomeImage>
        {/* <HomeRoundedIcon style={{ fontSize: "4rem" }} /> */}
        {/* 홈으로 */}
      </ButtomBarHome>
      <ButtomBarContents onClick={() => navigate("profile/schedule")}>
        <EventNoteRoundedIcon style={{ fontSize: "3.3rem" }} />
        스케줄
      </ButtomBarContents>
      <ButtomBarContents onClick={() => navigate("profile")}>
        <PersonRoundedIcon style={{ fontSize: "3.3rem" }} />
        마이페이지
      </ButtomBarContents>
      <Auth open={authOpen} onClose={handleAuthClose} />
    </BottomBarContainer>
  );
}

export default MobileBottomBar;
