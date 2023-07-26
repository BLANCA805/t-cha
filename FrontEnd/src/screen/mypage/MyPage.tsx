import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const Wrapper = styled.div`
  display:flex;
  flex-direction:column;
  align-items:center;
  margin:3%;
 
`;

const ContainerSet = styled.div`
  display:flex;
  justify-content: center;
  align-items:center;
  width:100%;
  margin-bottom:3%;
  
`;

const Profile =styled(ContainerSet)`
  display:flex;
  flex-direction: row;
  background-color: #fff;
  height:15rem;
  border-radius:10px;
  width:100%;
`;


const TrainerContainer =styled(ContainerSet)`
  display:flex;
  flex-direction: column;
  height:20rem;
  /* display:none; */
`;

const UserContainer =styled(ContainerSet)`
  display:flex;
  flex-direction: column;
  height:35rem;
`;

const ProfilePhoto = styled.div`
  flex:4;
  display:flex;
  justify-content: center;
  align-items:center;
  padding:5%;
  height:100%;
  width:100%;
`;

const Profileinfo = styled.div`
  flex:8;
  display:flex;
  flex-direction:column;
  justify-content: flex-start;
  padding:5%;
  height:100%;
  width:100%;
`;

const ProfilePhotoimg= styled.img`
  border-radius:50%;
  background-color: gray;
`;
const UserId = styled.div`
  flex:5.5;
  display:flex;
  align-items: end;
  font-weight:bold;
  font-size:4rem;
  margin-top:1px;
  margin-bottom:1px;
`;
const TrainerHashtag = styled.div`
  flex:2;
  font-weight:700;
  align-items: center;
  font-size:1.5rem;
  margin-top:1px;
  margin-bottom:1px;
  /* background-color: pink; */
`;
const TrainerIntroduct = styled.div`
  flex:4;
  font-size:1rem;
  margin-top:1px;
  margin-bottom:1px;
  /* background-color: lightcyan; */
`;

const TrRow = styled.div`
  display:flex;
  justify-content: center;
  align-items:center;
  flex:4;
  width:100%;
  background-color: white;
  border-radius:10px;
  margin-top:0.5%;
  margin-bottom:0.5%;
`;

const Usrow = styled.div`
  display:flex;
  flex-direction:row;
  justify-content: center;
  flex:6;
  width:100%;
  
`;

const Uscol = styled.div`
  flex:3;
  display:flex;
  justify-content: center;
  align-items:center;
  background-color: white;
  margin:3px;
  border-radius:10px;
`;



function MyPage() {
  return (

  <Wrapper>
    <Profile>
      <ProfilePhoto>
        <ProfilePhotoimg src="/logo192.png"/>
      </ProfilePhoto>
      <Profileinfo>
        <UserId>Username</UserId>
        <TrainerHashtag>#Tag1   #Tag2   #Tag3</TrainerHashtag>
        <TrainerIntroduct>Introduction who am I</TrainerIntroduct>
      </Profileinfo>
    </Profile>
    <TrainerContainer>
      <TrRow>
        <Link to="">트레이너 정보 수정 (트레이너 상세 페이지)</Link>
      </TrRow>
      <TrRow>
        <Link to="">트레이너 스케줄 관리, 일정</Link>
      </TrRow>
      <TrRow>
        <Link to="">내 회원 리스트</Link>
      </TrRow>
    </TrainerContainer>
    <UserContainer>
      <Usrow>
        <Uscol>
          <Link to="bookmark">즐찾트레이너</Link>
        </Uscol>
        <Uscol>
          <Link to="calendar">캘린더</Link>
        </Uscol>
        <Uscol>
          <Link to="my-review">내리뷰</Link>
        </Uscol>
      </Usrow>
      <Usrow>
        <Uscol>
          <Link to="payment">결제정보</Link>
        </Uscol>
        <Uscol>
          <Link to="/chat">채팅목록</Link>
        </Uscol>
        <Uscol>
          <Link to="/customer-center">고객센터</Link>
        </Uscol>
      </Usrow>

    </UserContainer>


  </Wrapper>
  
  );
}

export default MyPage;
