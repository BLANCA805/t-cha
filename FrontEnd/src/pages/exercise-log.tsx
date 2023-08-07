import React, {useRef} from "react";
import { useDispatch, useSelector } from "react-redux";
import { type RootState } from "../redux/store";
import { useState, ChangeEvent } from "react";
import axios from "axios";

import { api } from "@shared/common-data";
import { registTrainer } from "src/redux/slicers";
import { SmallTitleWrapper, SmallPageTitleText } from "@shared/page-title";
import TextField from "@mui/material/TextField";
import { Typography } from "@mui/material";
import { TchaButton, GrayButton,DefaultButton } from "@shared/button";
import styled, { isStyledComponent } from "styled-components";



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
const InputCustomButton=styled(TchaButton)`
  margin:0% 12% !important;
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
  
  //imageInput 커스터마이징 -useref로 input태그에 접근해서 클릭이벤트 연결, 
  //원래input은 안보이게 수정 (display:"none")
  const imageInput = React.useRef<HTMLInputElement>(null);
  const onClickImageUpload = () => {
    if (imageInput.current) {
      imageInput.current.click();
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
    <Wrapper>
      <SmallTitleWrapper>
          일지쓰기
      </SmallTitleWrapper>
      <Container>
        <Form onSubmit={register}>
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
            <input
              type="file"
              accept="image/jpg,impge/png,image/jpeg,image/gif"
              name="trainer_img"
              onChange={handleImage}
              style={{display:"none"}}
              ref={imageInput}
              ></input>
              <InputCustomButton onClick={onClickImageUpload}
                style={{width:"7rem", height:"3rem",fontSize:"1rem"}}
                variant="contained" >
                사진등록
              </InputCustomButton>

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
            <GrayButton 
              type="submit"
              style={{width:"7rem", height:"3rem",fontSize:"1rem"}} 
              variant="contained">
                임시저장
            </GrayButton>
            <TchaButton 
              type="submit"
              style={{width:"7rem", height:"3rem",fontSize:"1rem"}} 
              variant="contained">
                작성완료
            </TchaButton>
            <TchaButton 
              //원래 있던페이지로 돌아가는 Linkto 코드 필요 
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

export default ExerciseLog;
