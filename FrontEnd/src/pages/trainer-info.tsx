import { Link, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";

import ToggleButtons from "@shared/toggle-button";

import styled from "styled-components";
import TrainerDetail from "@trainer-info/trainer-detail";
import TrainerReview from "@trainer-info/trainer-review";

const Container = styled.div``;

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.light};
`;

function TrainerInfo() {
  const trainer = useLocation().state;

  const [state, setState] = useState<string>("detail");
  const [detail, setDetail] = useState([]);
  const [review, setReview] = useState("");

  const clickTab = (name: string) => {
    setState(name); // 새로운 탭 클릭 시, 상태 변경
  };

  useEffect(() => {
    axios
      .all([
        axios.get(`http://70.12.245.39:8080/trainers/${trainer}`),
        axios.get(`http://70.12.245.39:8080/reviews/trainers/${trainer}`),
      ])
      .then(
        axios.spread((detail, review) => {
          setDetail(detail.data);
          console.log(detail.data);
          setReview(review.data);
          console.log(review.data);
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
        {<TrainerDetail />}
        {state === "review" && <TrainerReview />}
      </Wrapper>
    </Container>
  );
}

export default TrainerInfo;
