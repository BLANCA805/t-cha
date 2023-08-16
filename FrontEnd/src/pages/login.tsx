import { useNavigate } from "react-router-dom";

import { TchaButton, GreenTchaButton } from "@shared/button";
import Logo from "src/shared/icons/LOGO.png";
import MyFont1 from "src/assets/fonts/jamsilOtfThin1.otf";
import styled from "styled-components";

const Wrapper = styled.div`
  width:100%;
  height:100vh;
  background-color: ${({ theme }) => theme.color.secondary};
  display: flex;
  justify-content: center;
  align-items: center;
`
const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-width:70%;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.color.light};
  padding: 16px;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);

`;

const ContentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width:100%;
  padding-bottom:5%;
`;

const ImageWrapper = styled.div`
  width: 80%;
  aspect-ratio: 1/1;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const Image = styled.img`
  margin-top: 5%;
  margin-right: 5%;
  width: 65%;
  /* height:50%; */
`;

const StyledButton = styled(GreenTchaButton)`
  width: 70%;
  text-align: center;
  margin-bottom: 5px;
  color:white !important;
`

const StyledText = styled.p`
  font-family: url(${MyFont1}) format('opentype') !important;
  font-size:0.95rem;
  color:${({ theme }) => theme.color.primary};
  text-align: center;
  margin: 15px 10px 15px 10px;
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
    <Wrapper>
      <Container>
        <ImageWrapper>
          <Image src={Logo} />
        </ImageWrapper>
        <ContentsWrapper>
          <StyledText>SNS 계정으로 간편 로그인/회원가입</StyledText>
          <StyledButton onClick={Sign}>
            Google로 로그인하기
            {/* <TchaButtonTextH6 style={{fontSize:"0.9rem"}}>Google로 로그인하기</TchaButtonTextH6> */}
          </StyledButton>
          <StyledButton onClick={backToHome}>
            홈으로 돌아가기
          </StyledButton>
        </ContentsWrapper>
      </Container>
    </Wrapper>
  );
};

export default Login;
