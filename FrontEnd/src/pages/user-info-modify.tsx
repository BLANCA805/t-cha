import TextField from "@mui/material/TextField";
import { TchaButton } from "@shared/button";
import { api } from "@shared/common-data";
import { SmallTitleWrapper } from "@shared/page-title";
import axios from "axios";
import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { RootState } from "src/redux/store";

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

function UserInfoModify() {
  const profileId = useSelector((state: RootState) => state.profile.profileId);
  const [name, setName] = useState("");
  const [file, setFile] = useState<File[]>([]);
  const [imageForView, setImageForView] = useState<string>("");

  useEffect(() => {
    axios.get(`${api}/userProfiles/${profileId}`).then((response) => {
      setName(response.data.name);
      setImageForView(response.data.profileImage);
      console.log(response.data);
    });
  }, [profileId]);

  const dispatch = useDispatch();

  const handleName = (event: any) => {
    setName(event.target.value);
  };

  const handleImage = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      const file = event.target.files[0];
      setFile([file]);
      console.log(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        setImageForView(reader.result as string);
      };
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

  const deleteImage = () => {
    setFile([]);
    setImageForView("");
  };

  const navigate = useNavigate();

  const Modify = async () => {
    const formData = new FormData();
    formData.append("images", file[0]);
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
      const profileUpdateData = {
        name: name,
        profileImage: imageUrl[0],
      };
      const profileUpdateResponse = await axios.patch(
        `${api}/userProfiles/${profileId}`,
        profileUpdateData
      );
      console.log("Profile Update Response:", profileUpdateResponse.data); // 프로필 업데이트 응답 로그 출력
      navigate("/profile");
    } catch (error) {
      console.log(error);
    }
  };

  const goToBack = () => {
    // navigate("/profile");
  };

  return (
    <Wrapper>
      <SmallTitleWrapper>내 정보 수정하기</SmallTitleWrapper>
      <Container>
        <Form>
          <FormDetailWrapper>
            <TextField
              value={name}
              label="이름"
              style={{ width: "30%" }}
              variant="outlined"
              onChange={handleName}
            />
          </FormDetailWrapper>

          <FormDetailWrapper>
            <input
              type="file"
              accept="image/jpg,impge/png,image/jpeg,image/gif"
              name="profile_img"
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
              프로필 이미지
            </InputCustomButton>
          </FormDetailWrapper>

          <ImageWrapper>
            <Image src={imageForView} onClick={() => deleteImage()} />
          </ImageWrapper>

          <SubmitButton>
            <TchaButton
              type="submit"
              style={{ width: "7rem", height: "3rem", fontSize: "1rem" }}
              variant="contained"
              onClick={Modify}
            >
              등록하기
            </TchaButton>
            <TchaButton
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

export default UserInfoModify;
