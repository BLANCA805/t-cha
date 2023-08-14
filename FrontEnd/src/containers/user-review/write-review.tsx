import { useSelector } from "react-redux";
import { useState, ChangeEvent } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import TextField from "@mui/material/TextField";
import { TchaButton } from "@shared/button";
import Modal from "@mui/material/Modal";
import Rating from "@mui/material/Rating";
import Typography from "@mui/material/Typography";

import styled from "styled-components";
import { RootState } from "src/redux/store";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
`;

const SubmitButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5% 0%;
`;

function WriteReview(props: { trainer: string; liveId: number }) {
  const user = useSelector((state: RootState) => state.profile.profileId);
  const trainer = props.trainer;
  const liveId = props.liveId;
  const [content, setContent] = useState<string>("");
  const [rating, setRating] = useState<number | null>(0);
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
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
      .patch(`${api}/reviews`, form)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <TchaButton onClick={handleOpen}>리뷰 작성하기</TchaButton>
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Wrapper>
          <TextField
            value={content}
            onChange={handleContent}
            label="내용을 입력하세요"
            style={{ width: "88%" }}
            variant="outlined"
          />

          <Typography component="legend">별점 입력하기</Typography>
          <Rating
            name="simple-controlled"
            value={rating}
            precision={0.5}
            onChange={(event, value) => {
              setRating(value);
            }}
          />

          <Typography component="legend">
            한번 작성한 리뷰는 수정이 불가합니다
          </Typography>

          <SubmitButton>
            <TchaButton
              type="submit"
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={postReview}
            >
              작성완료
            </TchaButton>
            <TchaButton
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={handleClose}
            >
              작성취소
            </TchaButton>
          </SubmitButton>
        </Wrapper>
      </Modal>
    </>
  );
}

export default WriteReview;
