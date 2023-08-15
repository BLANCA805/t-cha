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
import Avatar from '@mui/material/Avatar';

import styled from "styled-components";

const SideBarContainer = styled.div`
  display:flex;
  flex-direction: column;
  position: sticky;
  top:0;
  left:0;
  background-color: ${({ theme }) => theme.color.light};
  opacity: 75%;
  min-width:16.5rem;

  height:100vh;
`;
const StyledList = styled(List)`
  display:flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  
`
const ListItemStyled = styled(ListItem)`
  border-bottom: 0.2px solid ${({ theme }) => theme.color.secondary} !important;
  /* box-shadow: 0 -1px 0 ${({ theme }) => theme.color.primary} inset; */

  &:last-child {
    border-bottom: none; 
  }
`;

const SideBarItemWrapper = styled.h6`
  display:flex;
  /* width:100%; */
  font-size:1.4rem;
  margin: 1.8rem 1.4rem;
  cursor: pointer;
`
const LoginWrapper = styled.div`
  display:flex;
  align-items: center;
`

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
          <StyledList>
            <div>
            {!profile.profileId && (
              <button onClick={test}>프로필 생성하기</button>
            )}
            <SideBarItemWrapper style={{marginLeft:"1.6rem"}} onClick={() => navigate("profile")}>
              <LoginWrapper style={{marginTop:"0.5rem", marginBottom:"0.5rem"}}>
                <Avatar style={{marginRight:"0.8rem"}} src="/broken-image.jpg" />
                <b style={{margin:"0%", fontSize:"1.5rem"}}>유저이름</b>
                <b style={{marginLeft:"0.2rem", fontSize:"1.3rem"}}>님</b>
              </LoginWrapper>
            </SideBarItemWrapper>
            {[
              ["profile", "마이페이지"],
              ["", "home"],
              ["profile/schedule", "내 캘린더"],
              ["profile/chat", "채팅 목록"],
              ["trainer", "트레이너"],
              ["customer_center", "고객센터"],
            ].map((data, index) => (
              <ListItemStyled key={index} disablePadding>
                <ListItemButton onClick={() => navigate(data[0])}>
                  <SideBarItemWrapper >
                    {data[1]}
                  </SideBarItemWrapper>
                </ListItemButton>
                {/* <ListItemButton> */}
                  {/* <Link to={data[0]}> */}
                  {/* </Link> */}
                {/* </ListItemButton> */}
              </ListItemStyled>
            ))}
            </div>
            <SideBarItemWrapper onClick={LogOut} style={{color:"#E36E6E"}}>
              로그아웃
            </SideBarItemWrapper>
            {/* <button onClick={LogOut}>로그아웃</button> */}
          </StyledList>
      );
    } else {
      return (

          <List>
            <SideBarItemWrapper style={{marginLeft:"1.6rem"}} onClick={handleAuthOpen}>
              <LoginWrapper style={{marginTop:"0.5rem", marginBottom:"0.5rem"}}>
                <Avatar style={{marginRight:"0.8rem"}} src="/broken-image.jpg" />
                로그인
              </LoginWrapper>
            </SideBarItemWrapper>
            {[
              ["", "홈"],
              ["trainer", "트레이너"],
              ["customer_center", "고객센터"],
            ].map((data, index) => (
              <ListItemStyled key={index} disablePadding>
                <ListItemButton onClick={() => navigate(data[0])}>
                  <SideBarItemWrapper >
                    {data[1]}
                  </SideBarItemWrapper>
                </ListItemButton>
              </ListItemStyled>
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
