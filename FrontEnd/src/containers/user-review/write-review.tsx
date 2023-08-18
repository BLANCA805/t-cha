import { useSelector } from "react-redux";
import { useState, ChangeEvent } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import TextField from "@mui/material/TextField";
import { GreenTchaButton, TchaButton, TchaButtonTextH6 } from "@shared/button";
import Modal from "@mui/material/Modal";
import Rating from "@mui/material/Rating";
import Typography from "@mui/material/Typography";

import styled from "styled-components";
import { RootState } from "src/redux/store";
import { UserScheduleData } from "src/interface";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
  min-height: 40vh;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50%;
  @media (max-width: 767px) {
    width: 70%;
  }
`;

const StyledTextH6 = styled.h6`
  margin: 3% 6%;
  font-size: 1rem;
  color: grey;
  @media (max-width: 767px) {
    font-size: 0.85rem;
  }
`;

const SubmitButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5% 0%;
`;

const StyledButton = styled(GreenTchaButton)`
  margin: 0%;
  height: 3rem;
  width: 7rem;
  @media (max-width: 767px) {
    width: 6rem;
    height:1.6rem;
  }
`;
function WriteReview(props: {
  trainerName: string;
  trainer: string;
  liveId: number;
  setItem: React.Dispatch<React.SetStateAction<UserScheduleData>>;
}) {
  const user = useSelector((state: RootState) => state.profile.profileId);
  const trainer = props.trainer;
  const liveId = props.liveId;
  const trainerName = props.trainerName;
  const [content, setContent] = useState<string>("");
  const [rating, setRating] = useState<number | null>(0);
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
    console.log("props:", props);
  };
  const handleClose = () => setOpen(false);

  const handleContent = (event: ChangeEvent<HTMLInputElement>) => {
    setContent(event.target.value);
  };

  const postReview = () => {
    const form = {
      userId: user,
      trainerId: trainer,
      ptLiveId: liveId,
      content: content,
      star: rating,
    };
    axios
      .post(`${api}/reviews`, form)
      .then((response) => {
        console.log(response.data);
        props.setItem((prev) => (prev.reviewId = response.data.id));
        handleClose();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <StyledButton onClick={handleOpen}>
        <TchaButtonTextH6>리뷰 작성</TchaButtonTextH6>
      </StyledButton>
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Wrapper>
          <StyledTextH6 style={{marginTop:"5%", fontSize:"1.3rem", color:"black"}}>
            {trainerName} 트레이너
          </StyledTextH6>
          <StyledTextH6>별점 입력하기</StyledTextH6>
          <Rating
            name="simple-controlled"
            value={rating}
            precision={0.5}
            onChange={(event, value) => {
              setRating(value);
            }}
            style={{ marginBottom: "4%" }}
          />

          <TextField
            value={content}
            onChange={handleContent}
            label="내용을 입력하세요"
            style={{ width: "88%", marginBottom: "4%" }}
            multiline
            minRows={5}
            variant="outlined"
          />

          <StyledTextH6>
            한번 작성한 리뷰는 수정이 불가하오니 신중하게 작성해 주시기 바랍니다
          </StyledTextH6>

          <SubmitButton>
            <StyledButton
              type="submit"
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={postReview}
            >
              작성완료
            </StyledButton>
            <StyledButton
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={handleClose}
            >
              작성취소
            </StyledButton>
          </SubmitButton>
        </Wrapper>
      </Modal>
    </>
  );
}

export default WriteReview;
