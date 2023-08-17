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
  justify-content: start;
  align-items: center;
  padding: 1%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
  position: sticky;
  top: 1.6%;
  left: 69.5%;
  min-height: 50vh;
  width: 26.8%;
  @media (max-width: 767px) {
    width: 70%;
  }
`;

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-content: center;
  width: 80%;
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
    width: 5rem;
  }
  color: white !important;
`;

const StyledGrayButton = styled(StyledButton)`
  background-color: gray !important;
  color: white !important;
`;

function WriteExerciseLogInPtRoom(props: { ptLiveId: number }) {
  const liveId = props.ptLiveId;
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
          console.log(response.data);
          setTitle(response.data.title);
          const imageData = response.data.images;
          const contentData = response.data.contents;
          const content = [];
          for (let i in imageData) {
            const set = { image: imageData[i], text: contentData[i] };
            content.push(set);
          }
          setContents(content);
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
      const imageUrl = uploadResponse.data[0];
      const updatedContents = [...contents];
      updatedContents[index].image = imageUrl;
      setContents(updatedContents);
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
  };

  const temp = () => {
    console.log({
      title: title,
      contents: contents,
    });
    axios
      .patch(`${api}/exercise-logs/${exerciseLogId}`, {
        title: title,
        contents: contents,
      })
      .then((response) => {
        console.log(response.data);
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
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div>
      <div style={{ display: "flex", justifyContent: "center" }}>
        <StyledTchaButton onClick={handleOpen}>일지 작성하기</StyledTchaButton>
      </div>
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
        BackdropProps={{ invisible: true }}
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
                  accept="image/jpg,impge/png,image/jpeg,image/gif"
                  name="log_image"
                  onChange={(event) => handleImage(event, index)}
                ></input>
              </InputWrapper>

              <ImageWrapper>
                <Image
                  key={index}
                  src={content.image ? content.image : undefined}
                  alt=""
                  onClick={() => deleteImage(index)}
                />
              </ImageWrapper>
              <ContentTextWrapper>
                <TextField
                  value={content.text}
                  onChange={(event) => handleContent(event, index)}
                  label="내용을 입력하세요"
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

export default WriteExerciseLogInPtRoom;
