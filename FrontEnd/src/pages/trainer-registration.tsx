import React, { useState, ChangeEvent } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import { api } from "@shared/common-data";
import { type RootState } from "../redux/store";

import { registTrainer } from "src/redux/slicers";

import { TchaButton, GrayButton } from "@shared/button";
import { SmallTitleWrapper } from "@shared/page-title";

import TextField from "@mui/material/TextField";
import { Button, Typography } from "@mui/material";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  /* width:100%; */
  height: 100vh;
  margin: 1%;
  justify-content: start;
  align-content: center;
`;
const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  /* width:60%; */
  padding: 1%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
`;

const Form = styled.div`
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.light};
  width: 90%;
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
  margin: 0% !important;
`;

const TagWrapper = styled.div`
  display: flex;
  flex-direction: row;
`;

function TrainerRegistration() {
  const [introduction, setIntroduction] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tag, setTag] = useState("");
  const [enteredTags, setEnteredTags] = useState("");
  const [tagsForView, setTagsForView] = useState("");
  const [images, setImages] = useState<File[]>([]);
  const profileId = useSelector((state: RootState) => state.profile.profileId);

  const dispatch = useDispatch();

  const registrationForm = {
    introduction: introduction,
    title: title,
    content: content,
    tags: enteredTags.slice(0, -1),
  };

  const handleIntroduction = (event: any) => {
    setIntroduction(event.target.value);
  };
  const handleTitle = (event: any) => {
    setTitle(event.target.value);
  };
  const handleContent = (event: any) => {
    setContent(event.target.value);
  };
  const handleTag = (event: any) => {
    setTag(event.target.value);
  };
  const addTag = () => {
    if (tag) {
      setEnteredTags(enteredTags + tag + ",");
      setTagsForView(tagsForView + "#" + tag + " ");
      setTag("");
    }
  };
  const clearTag = () => {
    setEnteredTags("");
  };
  const handleImage = (event: ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files) {
      const file = files[0];
      console.log(files);
      setImages((prev) => [...prev, files]);
    } else {
      // 이미지를 선택하지 않은 경우에 대한 예외 처리
      console.log("No image selected.");
    }
  };

  //imageInput 커스터마이징 -useref로 input태그에 접근해서 클릭이벤트 연결,
  //원래input은 안보이게 수정 (display:"none")
  const imageInput = React.useRef<HTMLInputElement>(null);
  const onClickImageUpload = () => {
    if (imageInput.current) {
      imageInput.current.click();
    }
  };

  const navigate = useNavigate();

  const register = (event: any) => {
    event.preventDefault();
    const body = new FormData();
    body.append("dto", JSON.stringify(registrationForm));
    // images.forEach((image) => {
    //   body.append("images", image);
    // });
    axios
      .post(`${api}/trainers/${profileId}`, registrationForm)
      .then((response) => {
        if (response.data) {
          console.log(response.data);
          dispatch(registTrainer({ trainerId: response.data.id }));
          navigate("/profile");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const goToBack = () => {
    navigate("/profile");
  };

  const testForJW = () => {
    axios.post();
  };

  return (
    <Wrapper>
      <SmallTitleWrapper>트레이너 등록하기</SmallTitleWrapper>
      <Container>
        <Form>
          <FormDetailWrapper style={{ marginTop: "3%" }}>
            <TextField
              value={introduction}
              onChange={handleIntroduction}
              label="간단한 트레이너 자기소개를 작성해주세요 (최대 nn자)"
              style={{ width: "100%" }}
              variant="outlined"
            />
          </FormDetailWrapper>

          <FormDetailWrapper>
            <TextField
              value={tag}
              label="태그를 입력해주세요"
              style={{ width: "30%" }}
              variant="outlined"
              onChange={handleTag}
            />
            <TagWrapper>
              <TchaButton
                style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
                variant="contained"
                onClick={addTag}
              >
                추가
              </TchaButton>
              <TchaButton
                style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
                variant="contained"
                onClick={clearTag}
              >
                비우기
              </TchaButton>
            </TagWrapper>
            <TextField
              disabled
              value={tagsForView.slice(0, -1)}
              style={{ width: "100%" }}
            />
          </FormDetailWrapper>

          <FormDetailWrapper>
            <TextField
              value={title}
              onChange={handleTitle}
              label="PT 제목을 입력해주세요 (최대 nn자)"
              style={{ width: "100%" }}
              variant="outlined"
            />
          </FormDetailWrapper>

          <FormDetailWrapper>
            <input
              type="file"
              multiple
              accept="image/jpg,impge/png,image/jpeg,image/gif"
              name="trainer_img"
              onChange={handleImage}
              style={{ display: "none" }}
              ref={imageInput}
            ></input>
            <InputCustomButton
              onClick={onClickImageUpload}
              style={{
                width: "7rem",
                height: "3rem",
                marginLeft: "0%",
                fontSize: "1rem",
              }}
              variant="contained"
            >
              사진등록
            </InputCustomButton>
            <button onClick={testForJW}>정원이 누르기</button>
          </FormDetailWrapper>

          <FormDetailWrapper>
            <TextField
              value={content}
              onChange={handleContent}
              label="PT 내용을 상세히 입력해주세요"
              multiline
              minRows={15}
              style={{ width: "100%" }}
              variant="outlined"
            />
          </FormDetailWrapper>

          <SubmitButton>
            <TchaButton
              type="submit"
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={register}
            >
              등록하기
            </TchaButton>
            <TchaButton
              //원래 있던페이지로 돌아가는 Linkto 코드 필요
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={goToBack}
            >
              작성취소
            </TchaButton>
            {/* <button type="submit"></button> */}
          </SubmitButton>
        </Form>
      </Container>
    </Wrapper>
  );
}

export default TrainerRegistration;
