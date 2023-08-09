import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import { TrainerDetailData } from "src/interface";

import TrainerDetail from "@trainer-info/trainer-detail";
import TrainerReview from "@trainer-info/trainer-review";

import ToggleButtons from "@shared/toggle-button";
import { DefaultButton } from "@shared/button";

import StarRoundedIcon from "@mui/icons-material/StarRounded";
import StarOutlineRoundedIcon from "@mui/icons-material/StarOutlineRounded";

import { Button, Typography } from "@mui/material";
import styled from "styled-components";

import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  /* background-color: lightpink; */
`;

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.light};
`;

const Profile = styled.div`
  display: flex;
  flex-direction: row;
  /* height: 15rem; */
  border-radius: 10px;
  width: 100%;
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
  margin-bottom: 0.5rem;
  /* background-color: pink; */
`;

const HashTagWrapper = styled.div`
  display: flex;
  justify-content: start;
`;

const TrainerIntroduct = styled.div`
  flex: 4;
  font-size: 1rem;
  margin-top: 1px;
  margin-bottom: 1px;
  /* background-color: lightcyan; */
`;

const BottomTab = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  position: sticky;
  bottom: 0;
  /* min-height:4rem; */
  min-height: 9rem;
  width: 100%;
  background-color: ${({ theme }) => theme.color.light};
  border-top: 0.3rem solid ${({ theme }) => theme.color.secondary};
`;

const BookmarkWrapper = styled.div`
  flex: 2;
  display: flex;
  justify-content: center;
  align-items: center;
  /* border: 0.1rem solid; */
  /* background-color: #f3f3f3; */
`;
const RegisterWrapper = styled.div`
  flex: 10;
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-color: violet; */
`;

const BookmarkButton = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 75%;
  aspect-ratio: 1/1;
  margin-left: 20%;
  color: #276e68 !important;
  /* background-color: lightgrey; */
`;

const RegisterButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75%;
  width: 90%;
  background-color: #276e68 !important;
  /* background-color: ${({ theme }) => theme.color.primary}!important; */
  border-radius: 10px !important;
`;

function TrainerInfo() {
  const trainer = useLocation().state;
  const user = useSelector((state: RootState) => state.profile);

  const navigate = useNavigate();

  const [tab, setTab] = useState<string>("detail");
  const [detail, getDetail] = useState<TrainerDetailData>();

  const keywordTags = detail?.tags.split(",");

  //즐겨찾기 버튼 상태변경 -> 아이콘버전
  const [bookmark, setBookmark] = useState(false);

  const clickTab = (name: string) => {
    setTab(name); // 새로운 탭 클릭 시, 상태 변경
  };

  useEffect(() => {
    axios
      .get(`${api}/trainers/${trainer}`)
      .then((response) => {
        getDetail(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [trainer]);

  const moveToModify = () => {
    navigate("/profile/trainer_info_modify");
  };

  const moveToReservation = () => {
    navigate("/trainer/pt_reservation", { state: trainer });
  };

  console.log(keywordTags);

  return (
    <Container>
      <Wrapper>
        <Profile>
          <ProfilePhoto>
            <ProfilePhotoimg src="/logo192.png" />
          </ProfilePhoto>
          <Profileinfo>
            <UserId>{detail?.profileName}</UserId>
            <HashTagWrapper>
              {keywordTags?.map((tag) => (
                <TrainerHashtag key={tag}>#{tag}</TrainerHashtag>
              ))}
            </HashTagWrapper>
            <TrainerIntroduct>{detail?.title}</TrainerIntroduct>
          </Profileinfo>
          {trainer === user.trainerId && (
            <ProfileModify>
              <DefaultButton onClick={moveToModify}> 수정 </DefaultButton>
            </ProfileModify>
          )}
        </Profile>
      </Wrapper>
      <Wrapper>
        <ToggleButtons
          tabs={[
            { text: "트레이너 상세 정보", name: "detail" },
            { text: "트레이너 리뷰", name: "review" },
          ]}
          width="100%"
          clickTab={clickTab}
        />
        {tab === "detail" && detail && <TrainerDetail data={detail} />}
        {tab === "review" && <TrainerReview trainer={trainer} />}
      </Wrapper>
      {trainer === user.trainerId && (
        <BottomTab>
          <BookmarkWrapper>
            {/* <BookmarkButton onClick={toggleBookmarkIcon} backgroundImage = {bookmarkIcon}></BookmarkButton> */}
            <BookmarkButton onClick={() => setBookmark(!bookmark)}>
              {bookmark ? (
                <StarRoundedIcon style={{ fontSize: "7em" }} />
              ) : (
                <StarOutlineRoundedIcon style={{ fontSize: "7em" }} />
              )}
            </BookmarkButton>
          </BookmarkWrapper>
          <RegisterWrapper>
            <RegisterButton onClick={moveToReservation} variant="contained">
              <Typography variant="h4">예약 및 결제하기</Typography>
            </RegisterButton>
          </RegisterWrapper>
        </BottomTab>
      )}
    </Container>
  );
}

export default TrainerInfo;
