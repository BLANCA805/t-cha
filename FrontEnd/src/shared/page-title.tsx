
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
export const SmallTitleWrapper = styled.div`
    display:flex;
    /* justify-content: center; */
    align-items: center;
    justify-content: center;
    min-height:15%;
    margin-bottom:1%;
    border-radius: 0.5rem;
    /* background-color: ${({ theme }) => theme.color.light};  */

    font-size:3rem;
    font-weight:bolder;
    color:${({ theme }) => theme.color.dark};
`;


export const PageTitleText=styled.div`
  color: ${({ theme }) => theme.color.dark};
  padding-left:4rem;
  font-size:4rem;
  font-weight: bolder;
`;

export const SmallPageTitleText=styled.div`
  color: ${({ theme }) => theme.color.dark};
  padding-left:4rem;
  font-size:3rem;
  font-weight: bolder;
`;
