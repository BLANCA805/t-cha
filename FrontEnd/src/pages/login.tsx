import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const Container = styled(Box)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  background-color: white;
  padding: 16px;

  Button {
    width: 80%;
    background-color: #125b51;
    color: white;
    text-align: center;
    margin-bottom: 5px;
  }
`;

const ContentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ImageWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const Image = styled.img`
  /* max-width: 100%;
  max-height: 100%; */
`;

const Text = styled.div`
  text-align: center;
  margin-bottom: 15px;
`;

const Login = () => {
  const navigate = useNavigate();

  const Sign = () => {
    window.open("https://www.tcha.site/api/oauth2/authorization/google");
  };

  const backToHome = () => {
    navigate("/");
  };

  return (
    <Container>
      <ImageWrapper>
        <Image src="/logo192.png" />
      </ImageWrapper>
      <ContentsWrapper>
        <Text>SNS 계정으로 간편 로그인/회원가입</Text>
        <Button onClick={Sign}>Google로 로그인하기</Button>
        <Button onClick={backToHome}>홈으로 돌아가기</Button>
      </ContentsWrapper>
    </Container>
  );
};

export default Login;
