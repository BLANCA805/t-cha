import styled from "styled-components";
import { DefaultButton, TchaButton } from "@shared/button";
import { BookmarkedTrainerDataProps } from "src/interface";
import axios from "axios";
import { api } from "@shared/common-data";
import { useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  height: 15rem;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
  margin-bottom: 1%;
`;

const PhotoWrapper = styled.div`
  flex: 3.5;
  display: flex;
  height: 100%;
  width: 100%;
  justify-content: center;
  align-items: center;
`;

const ImgWrapper = styled.div`
  display: flex;
  aspect-ratio: 1/1;
  height: 80%;
`;

const TRimg = styled.img`
  border-radius: 50%;
  height: 100%;
  width: 100%;
  background-color: lightgray;
`;

const DataWrapper = styled.div`
  flex: 5.5;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 2%;
`;

const BookmarkWrapper = styled.div`
  flex: 3;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const NameWrapper = styled.div`
  margin-bottom: 1rem;
  margin-left: 3%;
`;

const KeywordWrapper = styled.div`
  margin-left: 3%;
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
        <ImgWrapper>
          <TRimg />
        </ImgWrapper>
      </PhotoWrapper>
      <DataWrapper>
        <NameWrapper>
          <b style={{ fontSize: "3rem" }}> {props.data.trainerName}</b>
          <b style={{ fontSize: "2rem", marginLeft: "1rem" }}>트레이너</b>
        </NameWrapper>
        <KeywordWrapper>
          {/* {props.keywordTags.map((tag, index) => (
            <b style={{ fontSize: "1.25rem", marginLeft: "1%" }}> #{tag}</b>
          ))} */}
        </KeywordWrapper>
      </DataWrapper>
      <BookmarkWrapper>
        {isBookmarked && (
          <DefaultButton onClick={deleteBookmark}>북마크해제</DefaultButton>
        )}
        {!isBookmarked && (
          <DefaultButton onClick={reBookmark}>북마크설정</DefaultButton>
        )}
      </BookmarkWrapper>
    </Wrapper>
  );
}

export default BookmarkedTrainerListItem;
