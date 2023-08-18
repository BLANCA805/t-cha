import { Link, Navigate, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { type RootState } from "../redux/store";

import TrainerButtons from "@user-trainer/trainer-buttons";

import SettingsRoundedIcon from "@mui/icons-material/SettingsRounded";
import { TchaButton } from "@shared/button";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { api } from "@shared/common-data";
import { userProfileData } from "src/interface";
import Asset3 from "src/shared/icons/Asset3.png";
import BookmarkTrIcon from "src/shared/icons/BookmarkTrIcon.png";
import CalenderIcon from "src/shared/icons/CalenderIcon.png";
import ChattingIcon from "src/shared/icons/ChattingIcon.png";
import MyPaymentIcon from "src/shared/icons/MyPaymentIcon.png";
import MyReviewIcon from "src/shared/icons/MyReviewIcon.png";
import NoticeIcon from "src/shared/icons/NoticeIcon.png";
import Logo from "src/shared/icons/LOGO.png";
import defaultImage from "src/shared/icons/default_profile.png";
import { useMediaQuery } from "react-responsive";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 96%;
  height: 100vh;
  margin: 1.25% 0%;
  @media (max-width: 767px) {
    margin: 2% 0%;
  }
  /* min-width:1520px; */
`;

const Profile = styled.div`
  display: flex;
  flex-direction: row;
  background-color: #fff;
  height: 20rem;
  border-radius: 10px;
  margin-bottom: 0.5%;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  color:${({ theme }) => theme.color.dark};
  @media (max-width: 767px) {
    height: 8rem;
    border-radius: 5px;
  }
`;
const TrRegister = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 2% 0%;
  background-color: #fff;
  height: 10rem;
  border-radius: 10px;
  width: 100%;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  cursor: url(${Asset3}), pointer;
  @media (max-width: 767px) {
    border-radius: 5px;
    height: 5rem;
  }
`;

const ProfilePhoto = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1%;
  width: 30%;
  aspect-ratio: 1/1;
  @media (max-width: 767px) {
    width: 27.5%;
  }
`;
const ProfilePhotoimg = styled.img`
  width: 80%;
  aspect-ratio: 1/1;
  overflow: hidden;
  object-fit: cover;
  border-radius: 1rem;
  background-color: gray;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
`;

const Profileinfo = styled.div`
  display: flex;
  align-items: center;
  margin-left: 4%;
  height: 100%;
  width: 100%;
  @media (max-width: 767px) {
    /* justify-content: center; */
    margin-left: 2%;
  }
`;

const UserId = styled.h4`
  display: flex;
  font-size: 6rem;
  @media (max-width: 767px) {
    font-size: 1.6rem;
  }
`;

const ProfileModify = styled.div`
  display: flex;
  width: 25%;
  align-items: center;
  justify-content: start;
  padding-right: 5%;
  @media (max-width: 767px) {
    padding-right: 2.5%;
    justify-content: center;
  }
`;

const UserContainer = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  /* height: 35rem; */
  @media (max-width: 767px) {
    flex-direction: column;
  }
`;

const Usrow = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
`;

const Uscol = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 33%;
  aspect-ratio: 1/1;
  margin: 0.3%;
  background-color: white;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  cursor: url(${Asset3}), pointer;
  @media (max-width: 767px) {
    border-radius: 4px;
    margin: 1%;
    &:first-child {
      margin-left: 0%;
    }
    &:last-child {
      margin-right: 0%;
    }
  }
`;
const IconWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 75%;
  aspect-ratio: 1/1;
  /* background-color: pink; */
`;
const Iconimg = styled.img`
  width: 70%;
  aspect-ratio: 1/1;
  object-fit: contain;
  /* background-color: #a8a8a8; */
`;

const StyledTextBig = styled.h5`
  margin: 2% 0%;
  font-size: 2.8rem;
  @media (max-width: 767px) {
    font-size: 1.3rem;
  }
`;
const StyledTextMid = styled.h5`
  margin: 2% 0%;
  font-size: 2rem;
  @media (max-width: 767px) {
    font-size: 0.9rem !important;
    margin-left: 4% !important;
  }
`;
const StyledTextSmall = styled.h6`
  margin: 2% 0%;
  font-size: 1.25rem;
  /* color:#a8a8a8; */
  @media (max-width: 767px) {
    font-size: 0.8rem;
  }
`;

function User() {
  const profile = useSelector((state: RootState) => state.profile);
  const [userData, setUserData] = useState<userProfileData>();
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`${api}/userProfiles/${profile.profileId}`)
      .then((response) => {
        setUserData(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [profile.profileId]);

  const isDesktop = useMediaQuery({ minWidth: 768 });
  const isMobile = useMediaQuery({ maxWidth: 767 });
  return (
    <Wrapper>
      <Profile>
        <ProfilePhoto>
          <ProfilePhotoimg
            src={
              userData?.profileImage ? userData.profileImage : defaultImage
            }
            alt={userData?.profileImage}
          />
        </ProfilePhoto>
        <Profileinfo>
          <UserId>{userData?.name.slice(0, 3)}</UserId>
          {/* <UserId>변정원</UserId> */}
          <StyledTextMid
            style={{ fontSize: "3rem", marginLeft: "4%", marginTop: "6%" }}
          >
            님, 안녕하세요!
          </StyledTextMid>
        </Profileinfo>
        <ProfileModify>
          {isDesktop && (
            <TchaButton
              onClick={() => navigate("info_modify")}
              style={{ width: "10rem", height: "5rem" }}
            >
              <StyledTextSmall style={{ color: "white", fontSize: "1.7rem" }}>
                수정하기
              </StyledTextSmall>
            </TchaButton>
          )}
          {isMobile && (
            <SettingsRoundedIcon
              onClick={() => navigate("info_modify")}
              style={{ fontSize: "2.7rem", color: "grey" }}
            />
          )}
        </ProfileModify>
      </Profile>

      {profile.trainerId && <TrainerButtons />}
      <UserContainer>
        <Usrow>
          <Uscol onClick={() => navigate("bookmarked_trainers")}>
            <IconWrapper>
              <Iconimg src={BookmarkTrIcon} />
            </IconWrapper>
            <StyledTextSmall>즐찾트레이너</StyledTextSmall>
            {/* <Link to="bookmarked_trainers">즐겨찾기 한 트레이너</Link> */}
          </Uscol>
          <Uscol onClick={() => navigate("schedule")}>
            <IconWrapper>
              <Iconimg src={CalenderIcon} style={{ marginTop: "5%" }} />
            </IconWrapper>
            <StyledTextSmall>스케줄</StyledTextSmall>
          </Uscol>
          <Uscol onClick={() => navigate("/profile/review")}>
            <IconWrapper>
              <Iconimg src={MyReviewIcon} style={{ marginTop: "6%" }} />
            </IconWrapper>
            <StyledTextSmall>My리뷰</StyledTextSmall>
          </Uscol>
        </Usrow>
        <Usrow>
          <Uscol onClick={() => navigate("payment_detail")}>
            <IconWrapper>
              <Iconimg src={MyPaymentIcon} style={{ marginTop: "3%" }} />
            </IconWrapper>
            <StyledTextSmall>결제정보</StyledTextSmall>
          </Uscol>
          <Uscol onClick={() => navigate("chat")}>
            <IconWrapper>
              <Iconimg src={ChattingIcon} />
            </IconWrapper>
            <StyledTextSmall>채팅목록</StyledTextSmall>
          </Uscol>
          <Uscol onClick={() => navigate("/customer_center")}>
            <IconWrapper>
              <Iconimg src={NoticeIcon} style={{ marginTop: "5%" }} />
            </IconWrapper>
            <StyledTextSmall>고객센터</StyledTextSmall>
          </Uscol>
        </Usrow>
      </UserContainer>
      {!profile.trainerId && (
        <TrRegister onClick={() => navigate("trainer_registration")}>
          <StyledTextBig> 트레이너 회원으로 등록하기 </StyledTextBig>
        </TrRegister>
      )}
    </Wrapper>
  );
}

export default User;
