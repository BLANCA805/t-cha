import { Link, Navigate, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { type RootState } from "../redux/store";
import { registTrainer, test } from "src/redux/slicers";

import { DefaultButton } from "@shared/button";
// import ReverseButton from "@shared/reversebutton";
import TrainerButtons from "@user-trainer/trainer-buttons";

// import Button from "@mui/material/Button";

import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { api } from "@shared/common-data";
import { userProfileData } from "src/interface";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 97%;
  margin: 3%;
`;

const ContainerSet = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-bottom: 3%;
`;

const Profile = styled(ContainerSet)`
  display: flex;
  flex-direction: row;
  background-color: #fff;
  height: 25rem;
  border-radius: 1rem;
  width: 100%;
`;
const TrRegister = styled(ContainerSet)`
  display: flex;
  flex-direction: row;
  background-color: #fff;
  height: 10rem;
  border-radius: 1rem;
  width: 100%;
  cursor: pointer;
`;

const UserContainer = styled(ContainerSet)`
  display: flex;
  flex-direction: column;
  height: 35rem;
`;

const ProfilePhoto = styled.div`
  flex: 2;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5%;
  height: 100%;
  width: 100%;
`;

const Profileinfo = styled.div`
  flex: 5;
  display: flex;
  flex-direction: row;
  padding: 5%;
  height: 100%;
  width: 100%;
`;

const ProfileModify = styled.div`
  flex: 4;
  display: flex;
  /* align-items: center; */
  justify-content: center;
`;

const ProfilePhotoimg = styled.img`
  width: 12rem;
  height: 12rem;
  overflow: hidden;
  object-fit: cover;
  border-radius: 1rem;
  background-color: gray;
`;

const UserId = styled.div`
  flex: 5.5;
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 4rem;
  margin-top: 1px;
  margin-bottom: 1px;
`;

const Usrow = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex: 6;
  width: 100%;
`;

const Uscol = styled.div`
  flex: 3;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  margin: 3px;
  border-radius: 10px;
  cursor: pointer;
`;

const StyledText = styled.h5`
  margin: 2% 0%;
  font-size: 2rem;
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

  return (
    <Wrapper>
      <Profile>
        <ProfilePhoto>
          <ProfilePhotoimg
            src={
              userData?.profileImage ? userData.profileImage : "/logo192.png"
            }
            alt={userData?.profileImage}
          />
        </ProfilePhoto>
        <Profileinfo>
          <UserId>{userData?.name}</UserId>
        </Profileinfo>
        <ProfileModify>
          <Link to="info_modify">
            <DefaultButton>수정하기</DefaultButton>
          </Link>
        </ProfileModify>
      </Profile>
      {!profile.trainerId && (
        <TrRegister onClick={() => navigate("trainer_registration")}>
          <StyledText> 트레이너 회원으로 등록하기 </StyledText>
        </TrRegister>
      )}
      {profile.trainerId && <TrainerButtons />}
      <UserContainer>
        <Usrow>
          <Uscol onClick={() => navigate("bookmarked_trainers")}>
            <StyledText> 즐겨찾는 트레이너 </StyledText>
            {/* <Link to="bookmarked_trainers">즐겨찾기 한 트레이너</Link> */}
          </Uscol>
          <Uscol onClick={() => navigate("schedule")}>
            <StyledText>나의 스케줄</StyledText>
          </Uscol>
          <Uscol onClick={() => navigate("review")}>
            <StyledText>내가 작성한 리뷰</StyledText>
          </Uscol>
        </Usrow>
        <Usrow>
          <Uscol onClick={() => navigate("payment_detail")}>
            <StyledText>결제 정보</StyledText>
          </Uscol>
          <Uscol onClick={() => navigate("chat")}>
            <StyledText>채팅 목록</StyledText>
          </Uscol>
          <Uscol onClick={() => navigate("/customer_center")}>
            <StyledText>고객센터</StyledText>
          </Uscol>
        </Usrow>
      </UserContainer>
    </Wrapper>
  );
}

export default User;
