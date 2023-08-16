import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import axios from "axios";

import { type RootState } from "src/redux/store";
import { api } from "@shared/common-data";

import styled from "styled-components";

import Avatar from "@mui/material/Avatar";
import StarRoundedIcon from "@mui/icons-material/StarRounded";
import StarOutlineRoundedIcon from "@mui/icons-material/StarOutlineRounded";
import { TrainerListDataProps } from "src/interface";
import { TchaButton } from "@shared/button";
import { Rating } from "@mui/material";
import { useState } from "react";

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 16rem;
  background-color: white;
  margin: 1%;
  padding: 1%;
  border-radius: 10px;
  display: flex;
  align-items: center;
  cursor: pointer;
  @media (max-width: 767px) {
    height: 7.5rem;
  }
`;

const AvatarWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin-left: 1%;
  /* max-width:15%; */
  aspect-ratio: 1/1;
  /* background-color: pink; */
  @media (max-width: 767px) {
    height: 70%;
  }
`;
const DataWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  width: 65%;
  /* margin-left:1%; */
  /* background-color: lightyellow; */
`;
const NameWrapper = styled.div`
  display: flex;
  margin: 2% 5% 2% 5%;
  align-items: end;
`;
const NameTextH5 = styled.h5`
  margin: 0%;
  font-size: 3rem;
  @media (max-width: 767px) {
    font-size: 1.5rem;
  }
`;
const TRTextH5 = styled.h5`
  font-size: 2rem;
  margin: 0% 0% 0% 2%;
  @media (max-width: 767px) {
    margin: 0% 0% 0% 4%;
    font-size: 0.9rem;
  }
`;
const KeywordWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: start;
  margin: 0% 1.5%;
  @media (max-width: 767px) {
    margin: 1.2% 3% 2% 3%;
    font-size: 0.7rem;
  }
`;

const Container = styled.div`
  display: flex;
`;

const TagTextH6 = styled.h5`
  font-size: 1.4rem;
  margin: 1.2% 3%;
  @media (max-width: 767px) {
    font-size: 0.7rem;
  }
`;
const DetailTextH6 = styled.h6`
  margin: 1.2% 2% 1.2% 5%;
  @media (max-width: 767px) {
    font-size: 0.5rem;
    margin: 1.2% 0.2% 1.2% 5%;
  }
`;
const ButtonWrapper = styled.div`
  display: flex;
  flex: 1;
  height: 100%;
  justify-content: center;
  align-items: center;
  /* background-color: pink; */
  @media (max-width: 767px) {
    justify-content: start;
  }
`;

const ButtonTextH5 = styled.h5`
  margin: 0%;
  color: white;
`;

const StyledStarOutlineRoundedIcon = styled(StarOutlineRoundedIcon)`
  font-size: 12em !important;
  @media (max-width: 767px) {
    font-size: 5em !important;
  }
`;

function TrainerListItem(props: TrainerListDataProps) {
  const navigate = useNavigate();

  const user = useSelector((state: RootState) => state.profile);
  const trainer = props.data.id;

  const bookmarkedUser = props.data.userProfileIdList;
  const [isBookmarked, setIsBookmarked] = useState(
    bookmarkedUser.includes(user.profileId)
  );

  const bookmark = () => {
    axios
      .post(`${api}/bookmarks/${user.profileId}/${trainer}`)
      .then((response) => {
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
      .then((response) => {
        console.log(trainer, "북마크 해제");
        setIsBookmarked(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const tags = props.data.tags.split(",");

  return (
    <Wrapper>
      <AvatarWrapper>
        <Avatar
          style={{ height: "80%", width: "80%", margin: "5%" }}
          alt=""
          src={props.data.profileImg}
        />
      </AvatarWrapper>
      <DataWrapper onClick={() => navigate(`info`, { state: trainer })}>
        <NameWrapper>
          <NameTextH5>{props.data.profileName}</NameTextH5>
          <TRTextH5>트레이너</TRTextH5>
          <Rating
            name="read-only"
            value={props.data.stars}
            readOnly
            size="large"
          />
        </NameWrapper>
        <KeywordWrapper>
          {tags.map((tag, index) => (
            <TagTextH6 key={index}>#{tag}</TagTextH6>
          ))}
        </KeywordWrapper>

        <DetailTextH6>등록 일자 : {props.data.createdAt}</DetailTextH6>

        <Container>
          <DetailTextH6>예약 수 : {props.data.ptCount}</DetailTextH6>
          <DetailTextH6>리뷰 수 : {props.data.reviewCount}</DetailTextH6>
          <DetailTextH6>재등록율 : {props.data.revisitGrade} %</DetailTextH6>
          <DetailTextH6>즐겨찾기 수 : {props.data.bookmarkCount}</DetailTextH6>
        </Container>
      </DataWrapper>
      <ButtonWrapper>
        {isBookmarked ? (
          <StarRoundedIcon
            onClick={cancleBookmark}
            style={{ fontSize: "5rem" }}
          />
        ) : (
          <StyledStarOutlineRoundedIcon
            onClick={bookmark}
            style={{ fontSize: "5rem" }}
          />
        )}
        {/* <TchaButton onClick={bookmark} style={{width:"70%"}}>
          <ButtonTextH5>즐겨찾기 등록</ButtonTextH5>
        </TchaButton> */}
      </ButtonWrapper>

      {/* 별 Toggle 북마크 로직 완성되면 아래 쓰면 됨  */}
      {/* <BookmarkWrapper onClick={() => setBookmark(!bookmark)}>
        {bookmark ? (
          <StarRoundedIcon style={{ fontSize: "2.5em" }} />
        ) : (
          <StarOutlineRoundedIcon style={{ fontSize: "2.5em" }} />
        )}
      </BookmarkWrapper> */}
    </Wrapper>
  );
}

export default TrainerListItem;
