import { useDispatch, useSelector } from "react-redux";
import { type RootState } from "../../redux/store";
import { useState, ChangeEvent } from "react";
import axios from "axios";

import TextField from "@mui/material/TextField";

import styled from "styled-components";

const Form = styled.form`
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.light};
`;

function TrainerInfoModify() {
  const [introduction, setIntroduction] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tags, setTags] = useState([]);
  const [images, setImages] = useState<File[]>([]);
  const trainerId = useSelector((state: RootState) => state.profile.trainerId);

  const dispatch = useDispatch();

  const form = {
    introduction: introduction,
    title: title,
    content: content,
    tags: tags,
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
  const handleTags = (event: any) => {
    setTags(event.target.value);
  };
  const handleImage = (event: ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files) {
      const file = files[0];
      console.log(file);
      setImages((prev) => [...prev, file]);
    } else {
      // 이미지를 선택하지 않은 경우에 대한 예외 처리
      console.log("No image selected.");
    }
  };

  const modify = (event: any) => {
    event.preventDefault();
    const body = new FormData();
    body.append("dto", JSON.stringify(form));
    // images.forEach((image) => {
    //   body.append("images", image);
    // });
    console.log(form);
    axios
      .patch(`http://70.12.245.39:8080/trainers/${trainerId}`, form)
      .then((response) => {
        if (response.data) {
          console.log(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Form onSubmit={modify}>
      <TextField
        value={introduction}
        label="Introduction"
        variant="outlined"
        onChange={handleIntroduction}
      />
      <TextField
        value={title}
        label="Title"
        variant="outlined"
        onChange={handleTitle}
      />
      <TextField
        value={content}
        label="Content"
        variant="outlined"
        onChange={handleContent}
      />
      <TextField
        value={tags}
        label="Tag"
        variant="outlined"
        onChange={handleTags}
      />
      <input
        type="file"
        accept="image/jpg,impge/png,image/jpeg,image/gif"
        name="trainer_img"
        onChange={handleImage}
      ></input>
      <button type="submit">제출하기</button>
    </Form>
  );
}

export default TrainerInfoModify;
