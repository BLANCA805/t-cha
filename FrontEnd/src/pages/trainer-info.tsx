import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useMediaQuery } from "react-responsive";
import axios from "axios";

import { api } from "@shared/common-data";
import { TrainerDetailData } from "src/interface";

import TrainerDetail from "@trainer-info/trainer-detail";
import TrainerReview from "@trainer-info/trainer-review";

import ToggleButtons from "@shared/toggle-button";
import {
  TchaButton,
  TchaStarFilled,
  TchaStarOutlined,
  TchaButtonText,
} from "@shared/button";

import StarRoundedIcon from "@mui/icons-material/StarRounded";
import StarOutlineRoundedIcon from "@mui/icons-material/StarOutlineRounded";
import SettingsRoundedIcon from "@mui/icons-material/SettingsRounded";

import { Button, Typography } from "@mui/material";
import styled from "styled-components";

import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 96%;
  min-height: 100vh;
  margin: 1.25% 0%;
  @media (max-width: 767px) {
    margin: 2% 0%;
  }

  /* background-color: lightpink; */
`;

const Wrapper = styled.div`
  display:flex;
  width:100%;
`

const TabWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 1%;
  width: 100%;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
`;

const Profile = styled.div`
  display: flex;
  flex-direction: row;
  background-color: #fff;
  min-height: 20rem;
  border-radius: 10px;
  margin-bottom: 0.5%;
  width: 100%;
  color: ${({ theme }) => theme.color.dark};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    min-height: 8rem;
    border-radius: 5px;
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
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
`;

const Profileinfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: start;
  height: 100%;
  width: 100%;
  margin-left: 4%;
`;

const IdWrapper = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
`;

const UserId = styled.h4`
  display: flex;
  font-size: 5.5rem;
  margin: 0%;
  @media (max-width: 767px) {
    font-size: 1.6rem;
  }
`;

const HashTagWrapper = styled.div`
  display: flex;
  width: 100%;
  margin: 1% 0% 0% 0%;
  justify-content: start;
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

const StyledTextBig = styled.h5`
  margin: 2.5% 0% 0% 3%;
  font-size: 3.4rem;
  @media (max-width: 767px) {
    font-size: 1.2rem;
    margin: 2% 0% 0% 3%;
  }
`;
const StyledTextMid = styled.h5`
  margin: 2% 5rem 2% 0%;
  font-size: 2rem;
  @media (max-width: 767px) {
    font-size: 0.9rem !important;
    margin: 2% 5% 2% 0% !important;
  }
`;
const StyledTextSmall = styled.h6`
  margin: 2% 0%;
  font-size: 1.25rem;
  /* color:#a8a8a8; */
  @media (max-width: 767px) {
    margin: 2.5% 0%;
    font-size: 0.6rem;
  }
`;

const BottomTab = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  position: sticky;
  bottom: 0;
  height: 9rem;
  width: 100%;
  background-color: ${({ theme }) => theme.color.light};
  border-top: 0.3rem solid ${({ theme }) => theme.color.secondary};
  @media (max-width: 767px) {
    position: fixed;
    height: 4.7rem;
    width: 100%;
    bottom: 5.5rem;
  }
`;

const ButtonText = styled.h5`
  font-size: 2.5rem;
  margin: 0%;
  @media (max-width: 767px) {
    font-size: 1.3rem;
  }
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
  margin-left: 13%;
  color: #276e68 !important;
  /* background-color: lightgrey; */
  @media (max-width: 767px) {
    margin-left: 24%;
  }
`;

const RegisterButton = styled(TchaButton)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80%;
  width: 95%;
  background-color: #276e68 !important;
  /* background-color: ${({ theme }) => theme.color.primary}!important; */
  border-radius: 10px !important;
  @media (max-width: 767px) {
    height: 75%;
    width: 90%;
    border-radius: 5px !important;
  }
`;

function TrainerInfo() {
  const trainer = useLocation().state;
  const user = useSelector((state: RootState) => state.profile);

  const navigate = useNavigate();

  const [tab, setTab] = useState<string>("detail");
  const [detail, getDetail] = useState<TrainerDetailData>();

  const keywordTags = detail?.tags.split(",");

  const clickTab = (name: string) => {
    setTab(name); // 새로운 탭 클릭 시, 상태 변경
  };

  const [isBookmarked, setIsBookmarked] = useState(false);

  useEffect(() => {
    axios
      .get(`${api}/trainers/${trainer}`)
      .then((response) => {
        getDetail(response.data);
        setIsBookmarked(
          response.data.userProfileIdList.includes(user.profileId)
        );
      })
      .catch((error) => {
        console.log(error);
      });
  }, [trainer]);

  const moveToModify = () => {
    navigate("/profile/trainer_info_modify", { state: detail });
  };

  const moveToReservation = () => {
    navigate("/trainer/pt_reservation", { state: trainer });
  };

  const isDesktop = useMediaQuery({ minWidth: 768 });
  const isMobile = useMediaQuery({ maxWidth: 767 });

  const bookmark = () => {
    axios
      .post(`${api}/bookmarks/${user.profileId}/${trainer}`)
      .then(() => {
        console.log(trainer, "북마크 등록");
        setIsBookmarked(true);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const cancleBookmark = () => {
    axios
      .delete(`${api}/bookmarks/${user.profileId}/${trainer}`)
      .then(() => {
        console.log(trainer, "북마크 해제");
        setIsBookmarked(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Container>
      <Profile>
        <ProfilePhoto>
          <ProfilePhotoimg src={detail?.profileImg} />
        </ProfilePhoto>
        <Profileinfo>
          <IdWrapper>
            <UserId>{detail?.profileName}</UserId>
            <StyledTextBig>트레이너</StyledTextBig>
          </IdWrapper>
          <HashTagWrapper>
            {keywordTags?.map((tag, index) => (
              <StyledTextMid key={index}>#{tag}</StyledTextMid>
            ))}
          </HashTagWrapper>
          <StyledTextSmall>{detail?.introduction}</StyledTextSmall>
        </Profileinfo>
        {trainer === user.trainerId && (
          <ProfileModify>
            {isDesktop && (
              <TchaButton
                onClick={() => moveToModify()}
                style={{ width: "10rem", height: "5rem" }}
              >
                <StyledTextSmall style={{ color: "white", fontSize: "1.7rem" }}>
                  수정하기
                </StyledTextSmall>
              </TchaButton>
            )}
            {isMobile && (
              <SettingsRoundedIcon
                style={{ fontSize: "2.7rem", color: "grey" }}
                onClick={() => moveToModify()}
              />
            )}
          </ProfileModify>
        )}
      </Profile>

      <TabWrapper>
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
      </TabWrapper>
      {trainer !== user.trainerId && (
        <BottomTab>
          <BookmarkWrapper>
            {isBookmarked ? (
              <TchaStarFilled
                onClick={cancleBookmark}
                style={{ fontSize: "7em" }}
              />
            ) : (
              <TchaStarOutlined
                onClick={bookmark}
                style={{ fontSize: "7em" }}
              />
            )}
          </BookmarkWrapper>
          <RegisterWrapper>
            <RegisterButton onClick={moveToReservation} variant="contained">
              <ButtonText>예약 및 결제하기</ButtonText>
            </RegisterButton>
          </RegisterWrapper>
        </BottomTab>
      )}
    </Container>
  );
}

export default TrainerInfo;
