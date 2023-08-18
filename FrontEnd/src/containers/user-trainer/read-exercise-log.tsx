import React, { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useState, ChangeEvent } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import { SmallTitleWrapper } from "@shared/page-title";
import TextField from "@mui/material/TextField";
import {
  TchaButton,
  GrayButton,
  GreenTchaButton,
  TchaButtonTextH6,
} from "@shared/button";
import Modal from "@mui/material/Modal";

import styled from "styled-components";
import { useLocation } from "react-router-dom";
import { LogData } from "src/interface";

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

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-content: center;
  width: 80%;
  height: 20%;
  padding: 1%;
  margin: 1.5% 0%;
  @media (max-width: 767px) {
    flex-wrap: wrap;
  }
`;

const InputWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20%;
  min-height: 100%;
  @media (max-width: 767px) {
    width: 90%;
    margin-left: 2%;
    margin-right: 5%;
    margin-bottom: 2%;
  }
`;

const ImageWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 30%;
  height: 100%;
  overflow: hidden;
  @media (max-width: 767px) {
    width: 40%;
    margin-bottom: 4%;
  }
`;

const ContentTextWrapper = styled.div`
  display: flex;
  align-items: center;
  width: 50%;
  min-height: 100%;
  @media (max-width: 767px) {
    width: 70%;
    margin-bottom: 7%;
  }
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 15%;
  @media (max-width: 767px) {
    width: 30%;
    margin-bottom: 7%;
  }
`;

const Image = styled.img`
  width: 80%;
  aspect-ratio: 1/1;
  /* max-height:5rem; */
  object-fit: contain;
  /* overflow: hidden; */
  margin: 1%;
  &:hover {
    opacity: 50%;
  }
`;

const FormDetailWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 80%;
  /* align-items: center; */
  margin-bottom: 1rem;
`;

const StyledButton = styled(GreenTchaButton)`
  margin: 0% !important;
  height: 3rem;
  width: 7rem;
  background-color: #1e3531 !important;
  @media (max-width: 767px) {
    width: 5rem;
  }
  color: white !important;
`;

function ReadExerciseLog(props: { liveId: number }) {
  const liveId = props.liveId;

  const [title, setTitle] = useState<string>("");
  const [contents, setContents] = useState<{ image: string; text: string }[]>([
    { image: "", text: "" },
  ]);

  const [open, setOpen] = React.useState(false);
  const [exerciseLogId, setExerciseLogId] = useState(0);

  useEffect(() => {
    if (open) {
      axios
        .get(`${api}/exercise-logs/ptLive/${liveId}`)
        .then((response) => {
          setTitle(response.data.title);
          const combinedContents = response.data.contents.map(
            (item: any, index: number) => ({
              image: response.data.images[index] || "", // images 배열의 요소가 없으면 빈 문자열로 초기화
              text: item,
            })
          );
          setContents(combinedContents);
          setExerciseLogId(response.data.id);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [open, liveId]);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
<<<<<<< FrontEnd/src/containers/user-trainer/read-exercise-log.tsx
    <div>
      <StyledButton onClick={handleOpen}><TchaButtonTextH6>일지보기</TchaButtonTextH6></StyledButton>
=======
    <>
      <StyledButton onClick={handleOpen}>
        <TchaButtonTextH6>일지 읽기</TchaButtonTextH6>
      </StyledButton>
>>>>>>> FrontEnd/src/containers/user-trainer/read-exercise-log.tsx
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Wrapper>
          <SmallTitleWrapper style={{ margin: "5% 0%" }}>
            {title}
          </SmallTitleWrapper>
          {contents.map((content, index) => (
            <Container key={index}>
              {index % 2 === 0 ? (
                <>
                  <ImageWrapper>
                    <Image
                      key={index}
                      src={content.image ? content.image : undefined}
                      alt={content.image}
                    />
                  </ImageWrapper>
                  <ContentTextWrapper>
                    <TextField
                      value={content.text}
                      label={content.text ? "" : "내용을 입력하세요"}
                      style={{ width: "95%" }}
                      variant="filled"
                      InputProps={{
                        style: { border: "none" },
                      }}
                    />
                  </ContentTextWrapper>
                </>
              ) : (
                <>
                  <ContentTextWrapper>
                    <TextField
                      value={content.text}
                      label={content.text ? "" : "내용을 입력하세요"}
                      style={{ width: "95%", float: "right" }}
                      variant="outlined"
                    />
                  </ContentTextWrapper>
                  <ImageWrapper>
                    <Image
                      key={index}
                      src={content.image ? content.image : undefined}
                      alt={content.image}
                    />
                  </ImageWrapper>
                </>
              )}
            </Container>
          ))}
        </Wrapper>
      </Modal>
    </>
  );
}

export default ReadExerciseLog;
