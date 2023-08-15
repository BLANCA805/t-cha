import React, { useEffect, useState } from "react";

import { TrainerReviewData, TrainerProps } from "src/interface";
import Pagination from "@mui/material/Pagination";
import Rating from "@mui/material/Rating";
import styled from "styled-components";
import axios from "axios";
import { api } from "@shared/common-data";

const TotalWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 3% 0%;
`;
const ContainerSet = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 5%;
  /* background-color: ${({ theme }) => theme.color.light}; */
`;

const ReviewContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  /* min-height:10rem; */
  padding: 3%;
  border-radius: 10px;
  background-color: #f3f3f3;
  margin-bottom: 1%;
`;

const ProfileWrapper = styled.div`
  display: flex;
  flex: 3;
  flex-direction: row;
  align-items: center;
  padding-bottom: 1%;
`;

const ContentsWrapper = styled.div`
  display: flex;
  font-size: 1rem;
  padding: 2% 1%;
`;

const UserProfileTextData = styled.div`
  display: flex;
  flex-direction: column;
  padding-left: 1.5%;
`;

const UserProfileimg = styled.img`
  height: 4rem;
  width: 4rem;
  border-radius: 50%;
  background-color: lightgray;
`;

const NameWrapper = styled.div``;
const DateRateWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const TrainerReview: React.FC<TrainerProps> = ({ trainer }) => {
  const [page, setPage] = React.useState(1);
  const handleChangePage = (
    event: React.ChangeEvent<unknown>,
    value: number
  ) => {
    setPage(value);
  };

  const [reviewData, setReviewData] = useState<TrainerReviewData>();

  useEffect(() => {
    axios
      .get(`${api}/reviews/trainer/${trainer}?page=${page}&size=10`)
      .then((response) => {
        setReviewData(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [page, trainer]);

  return (
    <TotalWrapper>
      <ContainerSet>
        {reviewData?.data.map((item, index) => (
          <ReviewContainer key={index}>
            <ProfileWrapper>
              <UserProfileimg />
              <UserProfileTextData>
                <NameWrapper>
                  <b style={{ fontSize: "1.5rem" }}> {item.id}</b>
                  <b style={{ fontSize: "1rem", marginLeft: "0.5rem" }}>
                    회원님
                  </b>
                </NameWrapper>

                <DateRateWrapper>
                  <Rating value={item.star} precision={0.5} readOnly />
                  <b style={{ fontSize: "1rem", marginRight: "1rem" }}>
                    {item.star}
                  </b>
                  <b style={{ fontSize: "0.7rem" }}>
                    작성자 : {item.profileName}
                  </b>
                </DateRateWrapper>
              </UserProfileTextData>
            </ProfileWrapper>

            <ContentsWrapper>{item.content}</ContentsWrapper>
          </ReviewContainer>
        ))}
      </ContainerSet>

      <Pagination
        count={reviewData?.pageInfo.totalPages}
        page={page}
        onChange={handleChangePage}
        color="standard"
      />
    </TotalWrapper>
  );
};

export default TrainerReview;
