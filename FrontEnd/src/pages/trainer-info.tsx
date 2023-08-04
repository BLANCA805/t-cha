import { Link, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

import {api} from "@shared/common-data";

import { TrainerReviewData, TrainerDetailData } from "src/interface";

import TrainerDetail from "@trainer-info/trainer-detail";
import TrainerReview from "@trainer-info/trainer-review";

import ToggleButtons from "@shared/toggle-button";
import { DefaultButton } from "@shared/button";

import styled from "styled-components";

const Container = styled.div``;

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
const TrainerIntroduct = styled.div`
  flex: 4;
  font-size: 1rem;
  margin-top: 1px;
  margin-bottom: 1px;
  /* background-color: lightcyan; */
`;




function TrainerInfo() {
  const trainer = useLocation().state;

  const [tab, setTab] = useState<string>("detail");
  const [detail, getDetail] = useState<TrainerDetailData>();
  const [review, getReview] = useState<TrainerReviewData>();

  const clickTab = (name: string) => {
    setTab(name); // 새로운 탭 클릭 시, 상태 변경
  };

  useEffect(() => {
    axios
      .all([
        axios.get(`${api}/trainers/${trainer}`),
        axios.get(`${api}/reviews/trainer/${trainer}`),
      ])
      .then(
        axios.spread((detail, review) => {
          getDetail(detail.data);
          getReview(review.data);
        })
      )
      .catch((error) => {
        console.log(error);
      });
  }, [trainer]);

  return (
    <Container>
      <Wrapper>
        <Profile>
          <ProfilePhoto>
            <ProfilePhotoimg src="/logo192.png" />
          </ProfilePhoto>
          <Profileinfo>
            <UserId>Username</UserId>
            {/* <UserId>{trainer.id}</UserId> */}
            <TrainerHashtag>#Tag1 #Tag2 #Tag3</TrainerHashtag>
            <TrainerIntroduct>Introduction who am I</TrainerIntroduct>
          </Profileinfo>
          <ProfileModify>
            <Link to="">
              <DefaultButton> 수정 </DefaultButton>
            </Link>
          </ProfileModify>
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
        {tab === "review" && review && (
          <TrainerReview data={review.data} pageInfo={review.pageInfo} />
        )}
      </Wrapper>
    </Container>
  );
}

export default TrainerInfo;
