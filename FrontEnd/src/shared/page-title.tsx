
import styled from "styled-components";

export const TitleWrapper = styled.div`
    display:flex;
    /* justify-content: center; */
    align-items: center;
    min-height:30%;
    margin-bottom:2%;
    border-radius: 0.5rem;
    background-color: ${({ theme }) => theme.color.light};; 
`;


export const PageTitleText=styled.div`
  color: ${({ theme }) => theme.color.dark};
  padding-left:4rem;
  font-size:4rem;
  font-weight: bolder;
`;
