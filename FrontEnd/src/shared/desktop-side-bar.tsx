import React, { useState } from "react";
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
import Avatar from "@mui/material/Avatar";

import styled from "styled-components";
import Asset3 from "src/shared/icons/Asset3.png";

const SideBarContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: sticky;
  top: 0;
  left: 0;
  background-color: ${({ theme }) => theme.color.light};
  opacity: 75%;
  width: 264px;
  flex-grow: 0;
  flex-shrink: 0;
  z-index: 1;
  height: 100vh;
`;
const StyledList = styled(List)`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
`;
const ListItemStyled = styled(ListItem)`
  border-bottom: 0.2px solid ${({ theme }) => theme.color.secondary} !important;
  /* box-shadow: 0 -1px 0 ${({ theme }) => theme.color.primary} inset; */

  &:last-child {
    border-bottom: none;
  }
  &:hover {
    color: #11a39c;
    cursor: url(${Asset3}), pointer !important;
  }
`;

const ListItembuttonStyled = styled(ListItemButton)`
  &:hover {
    cursor: url(${Asset3}), pointer !important;
  }
`;

const SideBarItemWrapper = styled.div`
  display: flex;
  width: 100%;
  font-size: 1.4rem;
  margin: 1.8rem 1.4rem;
  &:hover {
    color: #11a39c;
    cursor: url(${Asset3}), pointer !important;
  }
`;

const StyledText = styled.h6`
  font-size: 1.4rem;
  margin: 0%;
`;
const LoginWrapper = styled.div`
  display: flex;
  align-items: center;
`;

function DesktopSideBar() {
  const navigate = useNavigate();

  const user = useSelector((state: RootState) => state.auth);
  const profile = useSelector((state: RootState) => state.profile);

  function list() {
    if (user.accessToken) {
      return (
        <StyledList>
          <div>
            <SideBarItemWrapper
              style={{ marginLeft: "1.6rem" }}
              onClick={() => navigate("profile")}
            >
              <LoginWrapper
                style={{ marginTop: "0.5rem", marginBottom: "0.5rem" }}
              >
                <Avatar
                  style={{ marginRight: "0.8rem" }}
                  src={profile.profileImage}
                />
                <b style={{ margin: "0%", fontSize: "1.5rem" }}>
                  {profile.name}
                </b>
                <b style={{ marginLeft: "0.2rem", fontSize: "1.3rem" }}>님</b>
              </LoginWrapper>
            </SideBarItemWrapper>
            {[
              ["profile", "마이페이지"],
              ["", "HOME"],
              ["profile/schedule", "내 캘린더"],
              // ["profile/chat", "채팅 목록"],
              ["trainer", "트레이너"],
              ["customer_center", "고객센터"],
            ].map((data, index) => (
              <ListItemStyled key={index} disablePadding>
                <ListItembuttonStyled onClick={() => navigate(data[0])}>
                  <SideBarItemWrapper>
                    <StyledText>{data[1]}</StyledText>
                  </SideBarItemWrapper>
                </ListItembuttonStyled>
              </ListItemStyled>
            ))}
          </div>
          <SideBarItemWrapper onClick={LogOut} style={{ color: "#E36E6E" }}>
            <StyledText>로그아웃</StyledText>
          </SideBarItemWrapper>
        </StyledList>
      );
    } else {
      return (
        <List>
          <SideBarItemWrapper
            style={{ marginLeft: "1.6rem" }}
            onClick={handleAuthOpen}
          >
            <LoginWrapper
              style={{ marginTop: "0.5rem", marginBottom: "0.5rem" }}
            >
              <Avatar
                style={{ marginRight: "0.8rem" }}
                src="/broken-image.jpg"
              />
              <StyledText>로그인</StyledText>
            </LoginWrapper>
          </SideBarItemWrapper>
          {[
            ["", "홈"],
            ["trainer", "트레이너"],
            ["customer_center", "고객센터"],
          ].map((data, index) => (
            <ListItemStyled key={index} disablePadding>
              <ListItembuttonStyled onClick={() => navigate(data[0])}>
                <SideBarItemWrapper>
                  <StyledText>{data[1]}</StyledText>
                </SideBarItemWrapper>
              </ListItembuttonStyled>
            </ListItemStyled>
          ))}
        </List>
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

  const dispatch = useDispatch<AppDispatch>();

  const LogOut = () => {
    dispatch(logOut());
    dispatch(deleteProfile());
    navigate("/");
  };

  return (
    <SideBarContainer>
      <React.Fragment>{list()}</React.Fragment>
      <Auth open={authOpen} onClose={handleAuthClose} />
    </SideBarContainer>
  );
}

export default DesktopSideBar;
