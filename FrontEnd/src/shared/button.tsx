import styled from "styled-components";
import { Button, Typography } from "@mui/material";
import StarRoundedIcon from "@mui/icons-material/StarRounded";
import StarOutlineRoundedIcon from "@mui/icons-material/StarOutlineRounded";

export const DefaultButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.5em 1em;
  background-color: #125b51;
  color: white;
  border-radius: 5px;
  font-size: 1rem;
`;

export const ReverseButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.5em 1em;
  background-color: white;
  color: #125b51;
  border: 2px solid #125b51;
  border-radius: 5px;
  font-size: 1rem;
`;

export const TchaButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75%;
  width: 90%;
  margin: 1% 1% !important;
  /* background-color: #2e726c !important; */
  background: linear-gradient(45deg, #11a39c, #2a4d43);
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.2) !important;
  /* background-color: ${({ theme }) => theme.color.primary}!important; */
  border-radius: 5px !important;
  &:hover {
    background: linear-gradient(45deg, #0a8d85, #23413a);
    /* background-color: #235551 !important; */
  }
`;

export const GreenTchaButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75%;
  width: 90%;
  margin: 1% 1% !important;
  background-color: #2e726c !important;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.2) !important;
  border-radius: 5px !important;
  &:hover {
    background-color: #235551 !important;
  }
  &[disabled] {
    cursor: not-allowed;
    opacity:90% !important;
    background-color: #7B7B7B !important;
  }
`;
export const GrayTchaButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75%;
  width: 90%;
  margin: 1% 1% !important;
  background-color: #757575 !important;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.2) !important;
  border-radius: 5px !important;
  &:hover {
    background-color: #616161 !important;
  }
`;

export const ColorTchaButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75%;
  width: 90%;
  margin: 1% 1% !important;
  /* background: radial-gradient( #11a39c, #2a4d43); */
  border-radius: 5px !important;
`;

export const ReverseTchaButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75%;
  width: 90%;
  margin: 1% 1% !important;
  color: #2e726c !important;
  background-color: ${({ theme }) => theme.color.light}!important;
  border: 2px solid #2e726c !important;
  border-radius: 5px !important;
  &:hover {
    background-color:#ececec !important;
  }
  @media (max-width: 767px) {
    border: 1.5px solid #2e726c !important;
  }
`;

export const GrayButton = styled(TchaButton)`
  background-color: ${({ theme }) => theme.color.primary} !important;
`;


export const TchaButtonText = styled.h5`
  font-size:1rem;
  color:${({ theme }) => theme.color.light};
  margin:0%;
  /* @media (max-width: 767px) {
    font-size: 0.8rem;
  } */
`
export const TchaButtonTextH6 = styled.h6`
  font-size:1rem;
  color:${({ theme }) => theme.color.light};
  margin:0%;
  /* @media (max-width: 767px) {
    font-size: 0.8rem;
  } */
`
export const ColoredButtonText = styled.h5`
  font-size:1rem;
  margin:0%;
  background-image: linear-gradient(45deg, #11a39c, #2a4d43);
  color: transparent; 
  -webkit-background-clip: text; 
  background-clip: text;
  /* @media (max-width: 767px) {
    font-size: 0.8rem;
  } */
`

export const TchaStarFilled = styled(StarRoundedIcon)`
  color: ${({ theme }) => theme.color.tcha};
  &:hover {
    color: #11a39c;
  }
`
export const TchaStarOutlined = styled(StarOutlineRoundedIcon)`
  color: ${({ theme }) => theme.color.tcha};
  &:hover {
    color: #11a39c;
  }
`