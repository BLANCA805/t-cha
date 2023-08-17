import styled from "styled-components";
import { DefaultButton } from "@shared/button";
import { ReviewDataProps } from "src/interface";
import Rating from "@mui/material/Rating";

const Container = styled.div`
  min-height: 10rem;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
  margin-bottom: 0.5%;
`;

const Wrapper = styled.div`
  display: flex;
  height: 100%;
  padding: 3%;
`;

const TrainerProfileWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const ProfileImage = styled.img`
  height: 6rem;
  width: 6rem;
  margin-bottom: 1rem;
  object-fit: cover;
  border-radius: 40%;
  background-color: lightgray;
`;

const ReviewContentWrapper = styled.div`
  padding: 2rem;
  align-items: center;
`;

function UserReviewListItem(props: ReviewDataProps) {
  return (
    <Container>
      <Wrapper>
        <TrainerProfileWrapper>
          <ProfileImage src={props.data.trainerProfileImg} />
          <div>
            <b style={{ fontSize: "0.8rem" }}>트레이너</b>
            <b style={{ fontSize: "1.2rem" }}>
              {" "}
              {props.data.trainerProfileName}{" "}
            </b>
          </div>
        </TrainerProfileWrapper>
        <ReviewContentWrapper>
          <div>PT 수업일 : {props.data.startDate}</div>
          <Rating value={props.data.star} precision={0.5} readOnly />
          <div style={{ fontSize: "2rem", marginTop: "1rem" }}>
            {props.data.content}
          </div>
        </ReviewContentWrapper>
      </Wrapper>
    </Container>
  );
}

export default UserReviewListItem;
