import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import axios from "axios";

import { api } from "./common-data";
import { AppDispatch } from "src/redux/store";

import { logIn } from "src/redux/slicers";

import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";

import styled from "styled-components";
import Logo from "src/shared/icons/LOGO.png"
import { useNavigate } from "react-router-dom";
import { TchaButton, GreenTchaButton,ReverseTchaButton } from "./button";

interface AuthProps {
  open: boolean;
  onClose: () => void;
}

const ModalWrapper = styled(Modal)`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const Container = styled(Box)`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 300px;
  height: 400px;
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
  flex: 3;
`;

const ImageWrapper = styled.div`
  display: flex;
  flex: 6;
  align-items: center;
  justify-content: center;
`;

const Image = styled.img`
  object-fit: cover;
  width: 45%;
  margin: 3% 3% 0% 0%;
  /* max-height: 80%; */
`;

const Text = styled.div`
  text-align: center;
  margin-bottom: 15px;
`;

const Auth = ({ open, onClose }: AuthProps) => {
  const Sign = () => {
    window.open("https://www.tcha.site/api/oauth2/authorization/google");
  };
  return (
    <ModalWrapper
      open={open}
      onClose={onClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Container>
        <ImageWrapper>
          <Image src= {Logo} />
        </ImageWrapper>
        <ContentsWrapper>
          <Text>SNS 계정으로 간편 로그인/회원가입</Text>
          <GreenTchaButton onClick={Sign} style={{height: "2.5rem"}}>Google로 로그인하기</GreenTchaButton>
          <GreenTchaButton onClick={onClose} style={{height: "2.5rem"}}>Close modal</GreenTchaButton>
        </ContentsWrapper>
      </Container>
    </ModalWrapper>
  );
};

export default Auth;
