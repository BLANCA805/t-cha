import React from "react";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import styled from "styled-components";

interface AuthProps {
  open: boolean;
  onClose: () => void;
}

const ModalWrapper = styled(Modal)`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const ContentBox = styled(Box)`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 300px;
  height:400px;
  background-color: white; 
  box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16); 
  padding: 16px; 
`;

const Auth: React.FC<AuthProps> = ({ open, onClose }) => {
  return (
    <ModalWrapper
      open={open}
      onClose={onClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <ContentBox>
        <Typography id="modal-modal-title" variant="h6" component="h2">
          LOGO
        </Typography>
        <div>
        <Button onClick={onClose}>Google로 로그인하기</Button> 
        </div>
        {/* <Typography id="modal-modal-description" sx={{ mt: 2 }}>
          Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
        </Typography> */}
        <Button onClick={onClose}>Close modal</Button>
      </ContentBox>
    </ModalWrapper>
  );
};

export default Auth;


