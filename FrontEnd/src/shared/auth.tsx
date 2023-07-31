import React, { useState } from "react";
import axios from "axios";
import { connect } from "react-redux";

import { actionCreators } from "src/store";
import store from "src/store";

import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";

import styled from "styled-components";

interface AuthProps {
  open: boolean;
  onClose: () => void;
  userLogIn: any;
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

const Auth = ({ open, onClose, userLogIn }: AuthProps) => {
  // const [userData, setUserData] = useState({});

  const onClick = () => {
    // const api = "http://70.12.245.39:8080/users";
    // axios
    //   .post(api, { headers: {} })
    //   .then((response) => {
    //     setUserData(response.data);
    //     onClose();
    //     console.log(response.data.id);
    //   })
    //   .catch((error) => {
    //     console.log(error);
    //   });
    userLogIn();
    console.log(store.getState());
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
          <Button onClick={onClick}>Google로 로그인하기</Button>
          <Button onClick={onClose}>Close modal</Button>
        </ContentsWrapper>
      </Container>
    </ModalWrapper>
  );
};

function mapStateToProps(state: any) {
  console.log(state);
  return { state };
}

function mapDispatchToProps(dispatch: any) {
  return {
    userLogIn: () => dispatch(actionCreators.userLogIn()),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Auth);
