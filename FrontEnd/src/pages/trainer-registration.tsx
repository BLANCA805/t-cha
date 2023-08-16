import React, { useState, ChangeEvent, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import { api } from "@shared/common-data";
import { type RootState } from "../redux/store";

import { registTrainer } from "src/redux/slicers";

import {GreenTchaButton, TchaButtonText, TchaButtonTextH6 } from "@shared/button";
import { TitleWrapper, PageTitleText } from "@shared/page-title";

import TextField from "@mui/material/TextField";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  width: 96%;
  min-height:100vh;
  margin: 1%;
`;

const StyledTitleWrapper = styled(TitleWrapper)`
  margin:0% 0% 1% 0%;
`

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width:100%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
`;

const Form = styled.div`
  display: flex;
  flex-direction: column;
  width: 95%;
  background-color: ${({ theme }) => theme.color.light};
`;

const FormDetailWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: start;
  width:100%;
  /* align-items: center; */
  margin-bottom: 0.5%;
  @media (max-width: 767px) {
   margin-bottom: 1.5%; 
  }
`;

const SubmitButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 2% 0%;
`;

const InputCustomButton = styled(GreenTchaButton)`
  margin: 0.5% 0% !important;
`;

const TagWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  width:100%;
  @media (max-width: 767px) {
    justify-content: center;
    flex-wrap: wrap;
  }
`;

const TagTextField = styled(TextField)`
   width: 40%;
   padding-right:0.5rem;
   @media (max-width: 767px) {
    padding-right:0;
    margin:1% 0% !important;
    width:65%;
  } 
`

const ImageWrapper = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  overflow: hidden;
  min-height: 10rem;
  border: 0.1rem;
  border-style: solid;
  border-radius: 0.5rem;
  margin-bottom: 1%;
  align-items: center;
  border-color: #bdbcba;
  @media (max-width: 767px) {
    min-height: 7rem;
  }
`;

const Image = styled.img`
  width: 10rem;
  height: 10rem;
  overflow: hidden;
  margin: 1%;
  &:hover {
    opacity: 50%;
  }
`;

const ButtonText = styled(TchaButtonTextH6)`
  font-size: 1.25rem;
  @media (max-width: 767px) {
   font-size:1.1rem 
  }
`

function TrainerRegistration() {
  const [introduction, setIntroduction] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tag, setTag] = useState("");
  const [enteredTags, setEnteredTags] = useState("");
  const [tagsForView, setTagsForView] = useState("");
  const [files, setFiles] = useState<File[]>([]);
  const [imagesForView, setImagesForView] = useState<string[]>([]);

  const profileId = useSelector((state: RootState) => state.profile.profileId);

  const dispatch = useDispatch();

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
    // eslint-disable-next-line
    const regExp = /[ \{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;

    if (regExp.test(event.target.value)) {
      alert("특수문자는 입력할 수 없습니다");
    } else {
      setTag(event.target.value);
    }
  };
  const addTag = () => {
    if (tag) {
      setEnteredTags(enteredTags + tag + ",");
      setTagsForView(tagsForView + "#" + tag + " ");
      setTag("");
    }
  };
  const addTagToEnter = (event: any) => {
    if (event.key === "Enter") {
      setEnteredTags(enteredTags + tag + ",");
      setTagsForView(tagsForView + "#" + tag + " ");
      setTag("");
    }
  };
  const clearTag = () => {
    setEnteredTags("");
    setTagsForView("");
  };

  const handleImage = (event: ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files) {
      for (let file of files) {
        setFiles((prev) => [...prev, file]);
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
          setImagesForView((prev: string[]) => [
            ...prev,
            reader.result as string,
          ]);
        };
      }
    } else {
      console.log("이미지가 선택되지 않았습니다.");
    }
  };

  const imageInput = useRef<HTMLInputElement>(null);

  const onClickImageUpload = () => {
    if (imageInput.current) {
      imageInput.current.click();
    }
  };

  const deleteImage = (index: number) => {
    const updatedFiles = [...files];
    const updatedImagesForView = [...imagesForView];

    updatedFiles.splice(index, 1);
    updatedImagesForView.splice(index, 1);

    setFiles(updatedFiles);
    setImagesForView(updatedImagesForView);
  };

  const navigate = useNavigate();

  const Register = async () => {
    const formData = new FormData();
    for (let file of files) {
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
      const imageUrl = uploadResponse.data;
      const registrationForm = {
        introduction: introduction,
        title: title,
        content: content,
        tags: enteredTags.slice(0, -1),
        images: imageUrl,
      };
      const trainerRegistResponse = await axios.post(
        `${api}/trainers/${profileId}`,
        registrationForm
      );
      console.log(trainerRegistResponse.data);
      dispatch(registTrainer({ trainerId: trainerRegistResponse.data.id }));
      navigate("/profile");
    } catch (error) {
      console.log(error);
    }
  };

  const goToBack = () => {
    navigate("/profile");
  };

  return (
    <Wrapper>
      <StyledTitleWrapper>
        <PageTitleText>
          트레이너 등록하기
        </PageTitleText>
      </StyledTitleWrapper>
      <Container>
        <Form>
          <FormDetailWrapper style={{ marginTop: "2%" }}>
            <TextField
              value={introduction}
              onChange={handleIntroduction}
              label="간단한 트레이너 자기소개를 작성해주세요 (최대 nn자)"
              style={{ width: "100%" }}
              variant="outlined"
            />
          </FormDetailWrapper>

          <FormDetailWrapper>
            <TagWrapper>
              <TagTextField
                value={tag}
                label="태그를 입력해주세요"
                variant="outlined"
                onChange={handleTag}
                onKeyDown={(enter) => addTagToEnter(enter)}
              />
              <GreenTchaButton
                style={{
                  width: "7rem",
                  height: "3rem",
                  fontSize: "1rem",
                }}
                variant="contained"
                onClick={addTag}
              >
               <ButtonText>추가</ButtonText> 
              </GreenTchaButton>
              <GreenTchaButton
                style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
                variant="contained"
                onClick={clearTag}
              >
                <ButtonText>비우기</ButtonText> 
              </GreenTchaButton>
              <TagTextField
              disabled
              value={tagsForView.slice(0, -1)}
              style={{ marginLeft:"1%"}}
              />
            </TagWrapper>
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
              }}
              variant="contained"
            >
              <ButtonText>사진 선택</ButtonText> 
            </InputCustomButton>
          </FormDetailWrapper>

          <ImageWrapper>
            {imagesForView.map((image: string, index) => (
              <Image
                key={index}
                src={image}
                alt=""
                onClick={() => deleteImage(index)}
              />
            ))}
          </ImageWrapper>

          <FormDetailWrapper>
            <TextField
              value={content}
              onChange={handleContent}
              label="PT 내용을 상세히 입력해주세요"
              multiline
              minRows={6}
              style={{ width: "100%" }}
              variant="outlined"
            />
          </FormDetailWrapper>

          <SubmitButton>
            <GreenTchaButton
              type="submit"
              style={{ width: "7rem", height: "3rem", fontSize: "1rem", margin:"0% 2%" }}
              variant="contained"
              onClick={Register}
            >
              <ButtonText>등록하기</ButtonText> 
            </GreenTchaButton>
            <GreenTchaButton
              style={{ width: "7rem", height: "3rem", fontSize: "1rem", margin:"0% 2%" }}
              variant="contained"
              onClick={goToBack}
            >
              <ButtonText>작성취소</ButtonText> 
            </GreenTchaButton>

          </SubmitButton>
        </Form>
      </Container>
    </Wrapper>
  );
}

export default TrainerRegistration;
