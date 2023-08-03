import { Link, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import { TrainerReviewData, TrainerDetailData } from "src/interface";

import TrainerDetail from "@trainer-info/trainer-detail";
import TrainerReview from "@trainer-info/trainer-review";

import ToggleButtons from "@shared/toggle-button";

import styled from "styled-components";

const Container = styled.div``;

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.light};
`;

function TrainerInfo() {
  const trainer = useLocation().state;

  console.log(trainer);

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
        <h3>트레이너 상세 페이지 입니다.</h3>
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
