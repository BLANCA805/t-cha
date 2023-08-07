import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { type RootState } from "../redux/store";
import { registTrainer, test } from "src/redux/slicers";

import { DefaultButton } from "@shared/button";
// import ReverseButton from "@shared/reversebutton";
import TrainerButtons from "@user-trainer/trainer-buttons";

// import Button from "@mui/material/Button";

import styled from "styled-components";

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
  border-radius: 10px;
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
  flex-direction: column;
  justify-content: flex-start;
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
  border-radius: 50%;
  background-color: gray;
`;
const UserId = styled.div`
  flex: 5.5;
  display: flex;
  align-items: end;
  font-weight: bold;
  font-size: 4rem;
  margin-top: 1px;
  margin-bottom: 1px;
`;
const TrainerHashtag = styled.div`
  flex: 2;
  font-weight: 700;
  align-items: center;
  font-size: 1.5rem;
  margin-top: 1px;
  margin-bottom: 1px;
  /* background-color: pink; */
`;
const TrainerIntroduct = styled.div`
  flex: 4;
  font-size: 1rem;
  margin-top: 1px;
  margin-bottom: 1px;
  /* background-color: lightcyan; */
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
  const dispatch = useDispatch();

  const tester = () => {
    dispatch(test());
  };
  return (
    <Wrapper>
      <Profile>
        <ProfilePhoto>
          <ProfilePhotoimg src="/logo192.png" />
        </ProfilePhoto>
        <Profileinfo>
          <UserId>Username</UserId>
          <TrainerHashtag>#Tag1 #Tag2 #Tag3</TrainerHashtag>
          <TrainerIntroduct>Introduction who am I</TrainerIntroduct>
        </Profileinfo>
        <ProfileModify>
          <Link to="info_modify">
            <DefaultButton> 수정하기 </DefaultButton>
          </Link>
        </ProfileModify>
      </Profile>
      {!profile.trainerId && (
        <Link to="trainer_registration">
          <DefaultButton> 트레이너 등록하기 </DefaultButton>
        </Link>
      )}
      {profile.trainerId && <TrainerButtons />}
      <UserContainer>
        <Usrow>
          <Uscol>
            <Link to="bookmarked_trainers">즐찾트레이너</Link>
          </Uscol>
          <Uscol>
            <Link to="schedule">캘린더</Link>
          </Uscol>
          <Uscol>
            <Link to="review">내리뷰</Link>
          </Uscol>
        </Usrow>
        <Usrow>
          <Uscol>
            <Link to="payment_detail">결제정보</Link>
          </Uscol>
          <Uscol>
            <Link to="chat">채팅목록</Link>
          </Uscol>
          <Uscol>
            <Link to="/customer_center">고객센터</Link>
          </Uscol>
        </Usrow>
      </UserContainer>
      {profile.trainerId && (
        <DefaultButton onClick={tester}>트레이너 취소</DefaultButton>
      )}
    </Wrapper>
  );
}

export default User;
