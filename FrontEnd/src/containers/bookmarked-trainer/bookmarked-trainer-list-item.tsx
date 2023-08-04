import styled from "styled-components";
import { DefaultButton } from "@shared/button";

interface BookmarkedTrainerListItemProps {
  data: {
    id: number;
    name: string;
    keywordTags: string[];
    profileImg?: string;
  };
}

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

function BookmarkedTrainerListItem(props: BookmarkedTrainerListItemProps) {
  return (
    <Wrapper>
      <PhotoWrapper>
        <ImgWrapper>
          <TRimg />
        </ImgWrapper>
      </PhotoWrapper>
      <DataWrapper>
        <NameWrapper>
          <b style={{ fontSize: "3rem" }}> {props.data.name}</b>
          <b style={{ fontSize: "2rem", marginLeft: "1rem" }}>트레이너</b>
        </NameWrapper>
        <KeywordWrapper>
          {props.data.keywordTags.map((tag, index) => (
            <b style={{ fontSize: "1.25rem", marginLeft: "1%" }}> #{tag}</b>
          ))}
        </KeywordWrapper>
      </DataWrapper>
      <BookmarkWrapper>
        {/* 북마크 해제 버튼 클릭시 list에서 delete되는 로직 구현필요  */}
        <DefaultButton>북마크해제</DefaultButton>
      </BookmarkWrapper>
    </Wrapper>
  );
}

export default BookmarkedTrainerListItem;
