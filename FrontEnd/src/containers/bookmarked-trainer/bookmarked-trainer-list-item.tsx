import styled from "styled-components";
import { DefaultButton, TchaButton } from "@shared/button";
import { BookmarkedTrainerDataProps } from "src/interface";
import axios from "axios";
import { api } from "@shared/common-data";
import { useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";

import StarRoundedIcon from "@mui/icons-material/StarRounded";
import StarOutlineRoundedIcon from "@mui/icons-material/StarOutlineRounded";


const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content:space-between;
  width:48%;
  height: 15rem;
  /* opacity:75%; */
  color:#303030;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 15px 15px 0px 15px;
  margin: 1%;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    width:100%;
    height:8rem;
    margin:1% 0%;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  }
`;

const PhotoWrapper = styled.div`
  display: flex;
  height: 100%;
  max-width: 40%;
  aspect-ratio: 1/1;
  justify-content: center;
  align-items: center;
`;

const TRimg = styled.img`
  height: 70%;
  aspect-ratio: 1/1;
  border-radius: 25%;
  background-color: lightgray;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {

  }
`;

const DataWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items:start;
  width:100%;
  height:100%;
  @media (max-width: 767px) {
    align-items:center;
    margin-right:0%;
  }
`;

const NameWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  width:100%;
  margin-left: 5%;
`;

const BookmarkWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: start;
  
  margin-top: 2%;
  min-width:15%;
`;


const KeywordWrapper = styled.div`
  margin-left: 3%;
`;

const StyledStarFilled = styled(StarRoundedIcon)`
  color: ${({ theme }) => theme.color.tcha};
  font-size:5rem !important;
  &:hover {
    color: #11a39c;
  }
  @media (max-width: 767px) {
    font-size:3rem !important;
    margin-right:16%;
  }
`
const StyledStarOutlined = styled(StarOutlineRoundedIcon)`
  color: ${({ theme }) => theme.color.tcha};
  font-size:5rem !important;
  &:hover {
    color: #11a39c;
  }
  @media (max-width: 767px) {
    font-size:3rem !important;
    margin-right:16%;
  }
`

const NameTextH5 = styled.h4`
  font-size: 3rem;
  margin: 0%;
  @media (max-width: 767px) {
    font-size: 1.8rem;
  }
`;
const TRTextH6 = styled.h5`
  font-size: 2rem;
  margin: 4% 0% 0% 4%;
  
  @media (max-width: 767px) {
    font-size: 1.2rem;
  }
`;

function BookmarkedTrainerListItem(props: BookmarkedTrainerDataProps) {
  const [isBookmarked, setIsBookmarked] = useState(true);

  const user = useSelector((state: RootState) => state.profile.profileId);

  const reBookmark = () => {
    axios
      .post(`${api}/bookmarks/${user}/${props.data.trainerId}`)
      .then((response) => {
        console.log(response.data);
        setIsBookmarked(true);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const deleteBookmark = () => {
    axios
      .delete(`${api}/bookmarks/${user}/${props.data.trainerId}`)
      .then((response) => {
        console.log(response);
        setIsBookmarked(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Wrapper>
      <PhotoWrapper>
        <TRimg />
      </PhotoWrapper>
      <DataWrapper>
        <NameWrapper>
          <NameTextH5> {props.data.trainerName}</NameTextH5>
          <TRTextH6>트레이너</TRTextH6>
        </NameWrapper>
        <KeywordWrapper>
          {/* {props.keywordTags.map((tag, index) => (
            <b style={{ fontSize: "1.25rem", marginLeft: "1%" }}> #{tag}</b>
          ))} */}
        </KeywordWrapper>
      </DataWrapper>
      <BookmarkWrapper>
        {isBookmarked && (
          <StyledStarFilled onClick={deleteBookmark}>북마크해제</StyledStarFilled>
        )}
        {!isBookmarked && (
          <StyledStarOutlined onClick={reBookmark}>북마크설정</StyledStarOutlined>
        )}
      </BookmarkWrapper>
    </Wrapper>
  );
}

export default BookmarkedTrainerListItem;
