import { useSelector } from "react-redux";
import { type RootState } from "../../redux/store";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import { api } from "@shared/common-data";

import { SmallTitleWrapper} from "@shared/page-title";
import { Typography } from "@mui/material";
import { TchaButton, GrayButton} from "@shared/button";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";

import styled from "styled-components";

const Wrapper= styled.form`
  display: flex;
  flex-direction: column;
  /* width:100%; */
  height:100vh;
  margin:1%;
  justify-content: start;
  align-content:center;
  `;
const Container= styled.form`
  display:flex;
  justify-content: center;
  align-items:center;
  
  /* width:60%; */
  padding:1%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.light};
  width:90%;
  `;

const FormDetailWrapper = styled.form`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  /* align-items: center; */
  margin-bottom:1rem;
  `;

const SubmitButton=styled.form`
  display:flex;
  justify-content: center;
  align-items: center;
  margin:5% 0%;
  /* background-color: violet; */
`;

function WriteInquiry() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [status, setStatus] = useState(false);

  const navigate = useNavigate();

  const user = useSelector((state: RootState) => state.profile.profileId);

  const form = {
    userProfileId: user,
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
      .post(`${api}/questions`, form)
      .then((response) => {
        console.log(response.data);
        navigate(-1);
      })
      .catch((error) => {
        console.log(form);
        console.log(error);
      });
  };

  return (
    <Wrapper>
      <SmallTitleWrapper>
          문의하기
      </SmallTitleWrapper>
      <Container>
        <Form onSubmit={upLoad}>
          <FormDetailWrapper
            style={{marginTop:"3%"}}
            >
            <Typography variant="h5" style={{marginTop:"0.5rem"}}>
              제목:
            </Typography>
            <TextField
              value={title}
              onChange={handleTitle}
              label="제목을 입력하세요"
              style={{width:"88%"}}
              variant="outlined"
            />
          </FormDetailWrapper>
          
          <FormDetailWrapper>
          <FormControlLabel
            style={{marginLeft:"11%"}}
            control={<Checkbox onChange={handleStatus} />}
            label="비공개"
          />

          </FormDetailWrapper>

          <FormDetailWrapper>
            <Typography variant="h5" style={{marginTop:"0.5rem"}}>
              내용:
            </Typography>
            <TextField
              value={content}
              onChange={handleContent}
              label="내용을 입력하세요 "
              multiline minRows={5}
              style={{width:"88%"}}
              variant="outlined"
            />
          </FormDetailWrapper>
      

      <SubmitButton>
        <TchaButton 
          type="submit"
          style={{width:"7rem", height:"3rem",fontSize:"1rem"}} 
          variant="contained">
            작성완료
        </TchaButton>
        <TchaButton 
          href="/customer_center"
          style={{width:"7rem", height:"3rem",fontSize:"1rem"}} 
          variant="contained">
            작성취소
        </TchaButton>
        {/* <button type="submit"></button> */}
      </SubmitButton>
    </Form>
  </Container>
</Wrapper>
  );
}

export default WriteInquiry;
