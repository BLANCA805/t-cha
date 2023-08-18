import TextField from "@mui/material/TextField";
import { GreenTchaButton, TchaButton, TchaButtonText, TchaButtonTextH6 } from "@shared/button";
import { api } from "@shared/common-data";
import { TitleWrapper, PageTitleText } from "@shared/page-title";
import axios from "axios";
import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { modifyProfile } from "src/redux/slicers";
import { RootState } from "src/redux/store";
import { Avatar } from "@mui/material";
import AddAPhotoRoundedIcon from '@mui/icons-material/AddAPhotoRounded';
import DefaultImg from "src/shared/icons/default_profile.png";
import Asset3 from "src/shared/icons/Asset3.png"

import styled from "styled-components";

const Wrapper = styled.div`
  /* display: flex; */
  flex-direction: column;
  width:98%;
  height:100%;
  /* height: 100vh; */
  margin: 1%;
  justify-content: start;
  align-content: center;
`;
const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  /* width:60%; */
  max-width:100%;
  /* min-height:50%; */
  padding: 3%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
`;

const FormDetailWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  width:100%;
  margin-bottom: 1rem;
  @media (max-width: 767px) {
    margin-bottom: 2rem;
  }
`;


const ImageChangeWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`
const ImageWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position:relative;
  height: 20rem;
  width: 20rem;
  border-radius: 25%;
  /* background-color: #f1f1f1; */
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  margin: 5% 0%;
  /* overflow: hidden; */
  @media (max-width: 767px) {
    height: 10rem;
    width: 10rem;
  }
`;

const Image = styled.img`
  width:100%; 
  height:100%;
  border-radius: 25%;
  object-fit: cover;
  overflow: hidden;
  aspect-ratio : 1/1 ;
  background-color: #f1f1f1;
  
  &:hover {
    cursor: url(${Asset3}), pointer;
    opacity: 50%;
  }
`;

const StyledAddPhotoIcon = styled(AddAPhotoRoundedIcon)`
  font-size: 3.5rem !important;
  color: ${({ theme }) => theme.color.tchalight};
  @media (max-width: 767px) {
    font-size:2rem !important;
  }
`
const InputCustomButton = styled.div`
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  width:30%;
  aspect-ratio: 1/1;
  border-radius: 50%;
  bottom: 0%;
  right: 0%;
  margin: 0%;
  overflow: visible;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  transition: transform 0.05s ease-in-out;
  &:hover ${StyledAddPhotoIcon}{
      color: #2e726c !important;
  }
  &:hover{
    transform: scale(1.05);
  }
  &:active{
    transform: scale(0.9);
  }
  @media (max-width: 767px) {
    width:35%;
  }
`;



const StyledTextField = styled(TextField)`
  width: 95%;
  background-color: ${({ theme }) => theme.color.light};
  margin-top:5% !important;
`

const SubmitButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width:45%;
  margin: 2% 0%;
  @media (max-width: 767px) {
    width:80%;
  }
`;

const StyledTchaButton = styled(GreenTchaButton)`
  margin:0% 1% !important;
  height:3rem;
  width:30%;
  @media (max-width: 767px) {
    margin:0% 3% !important;
    width:45%;
  }
` 

function UserInfoModify() {
  const profileId = useSelector((state: RootState) => state.profile.profileId);
  const [name, setName] = useState("");
  const [file, setFile] = useState<File[]>([]);
  const [imageForView, setImageForView] = useState<string>(DefaultImg);

  useEffect(() => {
    axios.get(`${api}/userProfiles/${profileId}`).then((response) => {
      setName(response.data.name);
      // setImageForView(response.data.profileImage);
      setImageForView(response.data.profileImage || DefaultImg); 
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
    setImageForView(DefaultImg);
  };

  const IfDefault = () => {
    if(imageForView !== DefaultImg){
      deleteImage();
    }
  }

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
      dispatch(modifyProfile(profileUpdateData));
      console.log("Profile Update Response:", profileUpdateResponse.data);
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
      <TitleWrapper>
        <PageTitleText>내 정보 수정하기</PageTitleText>
        </TitleWrapper>
      <Container>
          <FormDetailWrapper>
            <ImageChangeWrapper>
              <ImageWrapper>
                <Image src={imageForView} onClick={() => IfDefault()} />
                <input
                  type="file"
                  accept="image/jpg,impge/png,image/jpeg,image/gif"
                  name="profile_img"
                  onChange={handleImage}
                  style={{ display: "none" }}
                  ref={imageInput}
                ></input>
                <InputCustomButton onClick={onClickImageUpload}>
                  <StyledAddPhotoIcon/>
                </InputCustomButton>
              </ImageWrapper>

            <StyledTextField
              value={name}
              label="이름"
              variant="outlined"
              onChange={handleName}
            />
            </ImageChangeWrapper>
          </FormDetailWrapper>

        
          <SubmitButton>
            <StyledTchaButton
              type="submit"
              variant="contained"
              onClick={Modify}
            >
              <TchaButtonTextH6 style={{fontSize:"1.2rem"}}>등록하기</TchaButtonTextH6>
            </StyledTchaButton>
            <StyledTchaButton
              variant="contained"
              onClick={goToBack}
            >
              <TchaButtonTextH6 style={{fontSize:"1.2rem"}}>작성취소</TchaButtonTextH6>
            </StyledTchaButton>
          </SubmitButton>
      </Container>
    </Wrapper>
  );
}

export default UserInfoModify;
