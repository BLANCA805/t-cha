import React, { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useState, ChangeEvent } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import { SmallTitleWrapper } from "@shared/page-title";
import TextField from "@mui/material/TextField";
import { TchaButton, GrayButton, DefaultButton } from "@shared/button";
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
`;

const Container = styled.div`
  display: flex;
  margin: 1%;
  justify-content: center;
  align-content: center;
`;

const FormDetailWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  /* align-items: center; */
  margin-bottom: 1rem;
`;

const SubmitButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5% 0%;
`;

const InputCustomButton = styled(TchaButton)`
  margin: 0% 12% !important;
`;

const ImageWrapper = styled.div`
  width: 100%;
  height: 100%;
  overflow: hidden;
  min-height: 20%;
`;

const Image = styled.img`
  width: 17%;
  height: 17%;
  overflow: hidden;
  margin: 1%;
  &:hover {
    opacity: 50%;
  }
`;

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function WriteExerciseLog(props: { liveId: number }) {
  const liveId = props.liveId;
  const [title, setTitle] = useState<string>("");
  const [contents, setContents] = useState<{ image: string; text: string }[]>([
    { image: "", text: "" },
  ]);
  const [open, setOpen] = React.useState(false);
  const [exerciseLogId, setExerciseLogId] = useState(0);

  useEffect(() => {
    axios
      .get(`${api}/exercise-logs/ptLive/${liveId}`)
      .then((response) => {
        setTitle(response.data.title);
        setContents(response.data.contents);
        setExerciseLogId(response.data.id);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [open, liveId]);

  const [files, setFiles] = useState<File[]>([]);

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
      console.log(index);
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
    axios
      .patch(`${api}/exercise-logs/${exerciseLogId}`)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const save = () => {
    axios
      .patch(`${api}/exercise-logs/done/${exerciseLogId}`)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div>
      <TchaButton onClick={handleOpen}>일지 작성하기</TchaButton>
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Wrapper>
          <SmallTitleWrapper>일지쓰기</SmallTitleWrapper>

          <FormDetailWrapper style={{ marginTop: "3%" }}>
            <TextField
              value={title}
              onChange={handleTitle}
              label="제목을 입력하세요"
              style={{ width: "88%" }}
              variant="outlined"
            />
          </FormDetailWrapper>

          {contents.map((content, index) => (
            <Container key={index}>
              <input
                type="file"
                accept="image/jpg,impge/png,image/jpeg,image/gif"
                name="log_image"
                onChange={(event) => handleImage(event, index)}
              ></input>

              <ImageWrapper>
                <Image
                  key={index}
                  src={content.image ? content.image : undefined}
                  alt=""
                  onClick={() => deleteImage(index)}
                />
              </ImageWrapper>

              <TextField
                value={content.text}
                onChange={(event) => handleContent(event, index)}
                label="내용을 입력하세요"
                style={{ width: "88%" }}
                variant="outlined"
              />

              <button onClick={() => removeContent(index)}>삭제하기</button>
            </Container>
          ))}

          <TchaButton
            type="submit"
            style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
            variant="contained"
            onClick={addContent}
          >
            내용 추가하기
          </TchaButton>

          <SubmitButton>
            <GrayButton
              type="submit"
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={temp}
            >
              임시저장
            </GrayButton>
            <TchaButton
              type="submit"
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={save}
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
    </div>
  );
}

export default WriteExerciseLog;
