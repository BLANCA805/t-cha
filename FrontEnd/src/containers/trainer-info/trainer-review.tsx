import React from "react";
import { TrainerReviewData } from "src/interface";
import Pagination from "@mui/material/Pagination";
import Rating from "@mui/material/Rating";
import styled from "styled-components";

const TotalWrapper = styled.div`
  display:flex;
  flex-direction: column;
  align-items: center;
  margin: 3% 0%;


`;
const ContainerSet=styled.div`
  display:flex;
  flex-direction: column;
  width:100%;
  margin-bottom: 5%;
  /* background-color: ${({ theme }) => theme.color.light}; */
`;

const ReviewContainer = styled.div`
  display:flex;
  flex-direction: column;
  height:100%;
  /* min-height:10rem; */
  padding:3%;
  border-radius: 10px;
  background-color: #f3f3f3;
  margin-bottom:1%;
`;


const ProfileWrapper = styled.div`
  display:flex;
  flex:3;
  flex-direction:row;
  align-items:center;
  padding-bottom:1%;
`;

const ContentsWrapper = styled.div`
  display:flex;
  font-size:1rem;
  padding: 2% 1%;
`;

const UserProfileTextData = styled.div`
  display:flex;
  flex-direction:column;
  padding-left:1.5%;
`;


const UserProfileimg = styled.img`
  height:4rem;
  width:4rem;
  border-radius: 50%;
  background-color: lightgray;
  `;

const NameWrapper = styled.div`
  
`;
const DateRateWrapper = styled.div`

`;



const TrainerReview: React.FC<TrainerReviewData> = ({ data }) => {

  data = [
    {id: "1병국",
      content: "알찬 수업 감사합니다 ",
      star: 5,
      created_at: "2023-06-03",},
    {id: "2병국",
      content: "그냥 그렇습니다",
      star: 3,
      created_at: "2023-06-03",},
    {id: "3병국",
      content: "수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 수업이 좋아요 ",
      star: 4,
      created_at: "2023-06-03",},
    {id: "4병국",
      content: "수업이 별로에요",
      star: 2.5,
      created_at: "2023-06-03",},
    {id: "5병국",
      content: "트레이너님 좋아요. ",
      star: 5,
      created_at: "2023-06-03",},
    {id: "임병국",
      content: "헤헷 ",
      star: 5,
      created_at: "2023-06-03",},
  ]
  
  console.log(data); 
  return (
    <TotalWrapper>
        {/* 트레이너 리뷰 페이지 입니다. */}
      <ContainerSet>
        {data.map((item) => (
          <ReviewContainer key={item.id}>
            <ProfileWrapper>
              <UserProfileimg />
              <UserProfileTextData>
                <NameWrapper>
                    <b style={{fontSize:"1.5rem"}}> {item.id}</b>
                    <b style={{fontSize:"1rem", marginLeft:"0.5rem"}}>회원님</b>
                </NameWrapper>

                <DateRateWrapper>
                  <Rating value={item.star} precision={0.5} readOnly />
                  <b style={{fontSize:"1rem", marginRight:"1rem"}}>{item.star}</b> 
                  <b style={{fontSize:"0.7rem"}}>{item.created_at}</b>
                </DateRateWrapper>
              </UserProfileTextData>
            </ProfileWrapper>

            <ContentsWrapper>
              {item.content}
            </ContentsWrapper>
          </ReviewContainer>
        ))}
      </ContainerSet>

      <Pagination count={5} color="standard" />
    </TotalWrapper>
  );
};

export default TrainerReview;
