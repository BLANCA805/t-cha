import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { logIn } from "src/redux/slicers";

import axios from "axios";

import {api} from "@shared/common-data";

import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";

import styled from "styled-components";
import { AppDispatch } from "src/redux/store";
import useAxios from "src/hooks/use-axios";

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
  /* max-width: 100%;
  max-height: 100%; */
`;

const Text = styled.div`
  text-align: center;
  margin-bottom: 15px;
`;

const Auth = ({ open, onClose }: AuthProps) => {
  const dispatch = useDispatch<AppDispatch>();

  const Sign = () => {
    axios
      .post(`${api}/users?email=email@gmail.com`)
      .then((response) => {
        if (response.data) {
          console.log(response.data);
          dispatch(
            logIn({
              token: response.data.id,
            })
          );
          onClose();
        }
      })
      .catch((error) => {
        console.log(error);
      });
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
          <Image src="/logo192.png" />
        </ImageWrapper>
        <ContentsWrapper>
          <Text>SNS 계정으로 간편 로그인/회원가입</Text>
          <Button onClick={Sign}>Google로 로그인하기</Button>
          <Button onClick={onClose}>Close modal</Button>
        </ContentsWrapper>
      </Container>
    </ModalWrapper>
  );
};

export default Auth;
