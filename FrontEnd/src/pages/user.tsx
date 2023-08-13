import { Link } from "react-router-dom";
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
  height: 15rem;
  border-radius: 1rem;
  width: 100%;
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
`;

function User() {
  const profile = useSelector((state: RootState) => state.profile);

  const [userData, setUserData] = useState<userProfileData>();

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
            <DefaultButton> 수정하기 </DefaultButton>
          </Link>
        </ProfileModify>
      </Profile>
      {!profile.trainerId && (
        <Profile>
          <Link to="trainer_registration">
            <DefaultButton> 트레이너 회원으로 등록하기 </DefaultButton>
          </Link>
        </Profile>
      )}
      {profile.trainerId && <TrainerButtons />}
      <UserContainer>
        <Usrow>
          <Uscol>
            <Link to="bookmarked_trainers">즐겨찾기 한 트레이너</Link>
          </Uscol>
          <Uscol>
            <Link to="schedule">나의 스케줄</Link>
          </Uscol>
          <Uscol>
            <Link to="review">내가 작성한 리뷰</Link>
          </Uscol>
        </Usrow>
        <Usrow>
          <Uscol>
            <Link to="payment_detail">결제 정보</Link>
          </Uscol>
          <Uscol>
            <Link to="chat">채팅 목록</Link>
          </Uscol>
          <Uscol>
            <Link to="/customer_center">고객센터</Link>
          </Uscol>
        </Usrow>
      </UserContainer>
    </Wrapper>
  );
}

export default User;
