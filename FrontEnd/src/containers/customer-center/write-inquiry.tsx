import { useSelector } from "react-redux";
import { type RootState } from "../../redux/store";
import { useState } from "react";
import axios from "axios";

import { api } from "@shared/common-data";

import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";

import styled from "styled-components";

const Form = styled.form`
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.light};
`;

function WriteInquiry() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [status, setStatus] = useState(true);

  const user = useSelector((state: RootState) => state.profile.profileId);

  const form = {
    title: title,
    content: content,
    status: status ? "QUESTION_PRIVATE" : "QUESTION_PUBLIC",
  };

  const handleTitle = (event: any) => {
    setTitle(event.target.value);
  };
  const handleContent = (event: any) => {
    setContent(event.target.value);
  };
  const handleStatus = (event: React.ChangeEvent<HTMLInputElement>) => {
    setStatus(event.target.checked);
  };

  const upLoad = (event: any) => {
    event.preventDefault();
    axios
      .post(`${api}/questions/`, form)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Form onSubmit={upLoad}>
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
      <FormControlLabel
        control={<Checkbox onChange={handleStatus} />}
        label="비공개"
      />
      <button type="submit">제출하기</button>
    </Form>
  );
}

export default WriteInquiry;
