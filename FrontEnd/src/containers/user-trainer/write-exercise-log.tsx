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

const SubmitButton = styled.div`
  display: flex;
  width: 60%;
  justify-content: space-evenly;
  align-items: center;
  margin: 5% 0% 5% 0%;
  @media (max-width: 767px) {
    width: 90%;
  }
`;

const StyledTchaButton = styled(TchaButton)`
  width: 40%;
  height: 3rem;
  margin-top: 3% !important;
  color: white !important;
  @media (max-width: 767px) {
    width: 60%;
  }
`;

const StyledButton = styled(GreenTchaButton)`
  margin: 0% 1.5% !important;
  height: 3rem;
  width: 7rem;

  @media (max-width: 767px) {
    margin:0% !important;
    height: 2rem;
    width: 5rem;
  }
  color: white !important;
`;
const StyledGrayButton = styled(StyledButton)`
  background-color: gray !important;
  color: white !important;
`;

function WriteExerciseLog(props: { liveId: number }) {
  const liveId = props.liveId;
  const [title, setTitle] = useState<string>("");
  const [contents, setContents] = useState<{ image: string; text: string }[]>([
    { image: "", text: "" },
  ]);

  const [imageForView, setImageForView] = useState<string[]>([]);

  const [open, setOpen] = React.useState(false);
  const [exerciseLogId, setExerciseLogId] = useState(0);

  console.log("확인" + exerciseLogId);

  //초기에 해당 운동일지에 대한 내용 가져옴 (image 제외)
  useEffect(() => {
    if (open) {
      axios
        .get(`${api}/exercise-logs/ptLive/${liveId}`)
        .then((response) => {
          setTitle(response.data.title);
          const combinedContents = response.data.contents.map(
            (item: string, index: number) => ({
              image: response.data.images[index]
                ? response.data.images[index]
                : "", // images 배열의 요소가 없으면 빈 문자열로 초기화
              text: item,
            })
          );
          console.log("들어오는 값 확인: " + combinedContents);
          setContents(combinedContents);
          setImageForView(response.data.images);
          setExerciseLogId(response.data.id);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [open, liveId]);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const addContent = () => {
    setContents([...contents, { image: "", text: "" }]);
  };

  const removeContent = (index: number) => {
    const removedContents = [...contents];
    removedContents.splice(index, 1);
    setContents(removedContents);
  };

  const handleTitle = (event: any) => {
    setTitle(event.target.value);
  };

  const handleContent = (
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    index: number
  ) => {
    const updatedContents = [...contents];
    updatedContents[index].text = event.target.value;
    setContents(updatedContents);
  };

  const handleImage = async (
    event: ChangeEvent<HTMLInputElement>,
    index: number
  ) => {
    const formData = new FormData();
    if (event.target.files) {
      const file = event.target.files[0];
      formData.append("images", file);
    }
    try {
      const uploadResponse = await axios.post(
        `${api}/upload/images`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log(uploadResponse);

      const imageUrl = uploadResponse.data[0];
      const updatedContents = [...contents];
      updatedContents[index].image = imageUrl;
      setContents(updatedContents);
      const updatedImageForView = imageForView;
      updatedImageForView.splice(index, 0, imageUrl);
      setImageForView(updatedImageForView);
    } catch (error) {
      console.log(error);
    }
  };

  const deleteImage = (index: number) => {
    const updatedContents = [...contents];
    const imageToDelete = updatedContents[index].image;
    const formData = new FormData();
    formData.append("image", imageToDelete);
    axios
      .delete(`${api}/upload/images`, {
        data: formData,
        headers: {
          "content-Type": "application/x-www-form-urlencoded",
        },
      })
      .then((response) => {
        console.log(response);
      });
    updatedContents[index].image = "";
    setContents(updatedContents);
    const updatedImageForView = imageForView;
    updatedImageForView.splice(index, 1);
    setImageForView(updatedImageForView);
  };

  const temp = () => {
    console.log("?????? ", title, contents);
    axios
      .patch(
        `${api}/exercise-logs/${exerciseLogId}`,
        {
          title: title,
          contents: contents,
        },
        {
          headers: {
            "Contest-Type": "application/json",
          },
        }
      )
      .then((response) => {
        console.log(response.data);
        alert("임시 저장 완료");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const save = () => {
    axios
      .patch(`${api}/exercise-logs/done/${exerciseLogId}`, {
        title: title,
        contents: contents,
      })
      .then((response) => {
        console.log(response.data);
        handleClose();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div>
      <StyledButton onClick={handleOpen}>
        {" "}
        <TchaButtonTextH6>일지쓰기</TchaButtonTextH6>
      </StyledButton>
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Wrapper>
          <SmallTitleWrapper style={{ margin: "5% 0%" }}>
            일지쓰기
          </SmallTitleWrapper>

          <FormDetailWrapper style={{ marginTop: "3%" }}>
            <TextField
              value={title}
              onChange={handleTitle}
              label="제목을 입력하세요"
              style={{ width: "100%" }}
              variant="outlined"
            />
          </FormDetailWrapper>

          {contents.map((content, index) => (
            <Container key={index}>
              <InputWrapper>
                <input
                  type="file"
                  accept="image/jpg,impge/png,image/jpeg,image/gif,image/jfif"
                  name="log_image"
                  onChange={(event) => handleImage(event, index)}
                ></input>
              </InputWrapper>

              <ImageWrapper>
                <Image
                  key={index}
                  src={imageForView[index]}
                  alt=""
                  onClick={() => deleteImage(index)}
                />
              </ImageWrapper>
              <ContentTextWrapper>
                <TextField
                  value={content.text}
                  onChange={(event) => handleContent(event, index)}
                  label={content.text ? "" : "내용을 입력하세요"}
                  style={{ width: "95%" }}
                  variant="outlined"
                />
              </ContentTextWrapper>
              <ButtonWrapper>
                <StyledButton onClick={() => removeContent(index)}>
                  삭제
                </StyledButton>
              </ButtonWrapper>
            </Container>
          ))}

          <StyledTchaButton
            type="submit"
            variant="contained"
            onClick={addContent}
          >
            내용 추가하기
          </StyledTchaButton>

          <SubmitButton>
            <StyledGrayButton
              type="submit"
              onClick={temp}
              style={{ backgroundColor: "gray" }}
            >
              임시저장
            </StyledGrayButton>
            <StyledButton type="submit" onClick={save}>
              작성완료
            </StyledButton>
            <StyledButton onClick={handleClose}>닫기</StyledButton>
          </SubmitButton>
        </Wrapper>
      </Modal>
    </div>
  );
}

export default WriteExerciseLog;
