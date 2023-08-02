
import styled from "styled-components";

export const TitleWrapper = styled.div`
    display:flex;
    justify-content: center;
    align-items: center;
    min-height:35%;
    margin-bottom:2%;
    border-radius: 5px;
    background-color: ${({ theme }) => theme.color.light};; 
`;


export const PageTitleText=styled.div`
  font-size:4rem;
  font-weight: bolder;
`;
