import styled from "styled-components";
import { Button, Typography } from "@mui/material";

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
  background-color: #2e726c !important;
  /* background-color: ${({ theme }) => theme.color.primary}!important; */
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
`;

export const GrayButton = styled(TchaButton)`
  background-color: ${({ theme }) => theme.color.primary} !important;
`;
