import styled from "styled-components";
import { DefaultButton } from "@shared/button";

const Container = styled.div`
  min-height: 10rem;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
  margin-bottom: 0.5%;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 3%;
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

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: end;
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
const DateRateWrapper = styled.div``;

interface reviewImgProps {
  src: string;
  alt: string;
  width?: number;
  height?: number;
}

interface UserReviewListItemProps {
  data: {
    id: number;
    username: string;
    date: string;
    rate: string;
    contents: string;
  };
}

function UserReviewListItem(props: UserReviewListItemProps) {
  return (
    <Container>
      <Wrapper>
        <ProfileWrapper>
          <UserProfileimg />
          <UserProfileTextData>
            <NameWrapper>
              <b style={{ fontSize: "1.5rem" }}> {props.data.username}</b>
              <b style={{ fontSize: "1rem", marginLeft: "0.5rem" }}>트레이너</b>
            </NameWrapper>

            <DateRateWrapper>
              <b style={{ fontSize: "1rem", marginRight: "1rem" }}>
                {props.data.rate}
              </b>
              <b style={{ fontSize: "0.7rem" }}>{props.data.date}</b>
            </DateRateWrapper>
          </UserProfileTextData>
        </ProfileWrapper>

        <ContentsWrapper>{props.data.contents}</ContentsWrapper>

        <ButtonWrapper>
          {/* 북마크 해제 버튼 클릭시 list에서 delete되는 로직 구현필요  */}
          <DefaultButton>리뷰삭제</DefaultButton>
        </ButtonWrapper>
      </Wrapper>
    </Container>
  );
}

export default UserReviewListItem;
