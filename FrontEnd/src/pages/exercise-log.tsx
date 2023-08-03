import { useDispatch, useSelector } from "react-redux";
import { type RootState } from "../redux/store";
import { useState, ChangeEvent } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import { registTrainer } from "src/redux/slicers";

import TextField from "@mui/material/TextField";

import styled from "styled-components";

const Form = styled.form`
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.light};
`;

function ExerciseLog() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [images, setImages] = useState<File[]>([]);

  const dispatch = useDispatch();

  const exerciseLogForm = {
    title: title,
    content: content,
  };
  const handleTitle = (event: any) => {
    setTitle(event.target.value);
  };
  const handleContent = (event: any) => {
    setContent(event.target.value);
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

  const register = (event: any) => {
    event.preventDefault();
    const body = new FormData();
    body.append(
      "dto",
      new Blob([JSON.stringify(exerciseLogForm)], { type: "application/json" })
    );
    // images.forEach((image) => {
    //   body.append("images", image);
    // });
    axios
      .post(`${api}/exercise-logs`, body)
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
    <Form onSubmit={register}>
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

export default ExerciseLog;
