
import styled from "styled-components";

export const TitleWrapper = styled.div`
    display:flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    height:12rem;
    width: 100%;
    margin-bottom:2%;
    border-radius: 0.5rem;
    background-color: ${({ theme }) => theme.color.light}; 
    /* background-color: ${({ theme }) => theme.color.dark};  */
    /* background: linear-gradient(45deg, #11a39c, #223a33); */
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);

    @media (max-width: 767px) {
      height:7rem;
      box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
    }
`;

export const ColorTitleWrapper = styled.div`
    display:flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    height:23rem;
    margin-bottom:2%;
    border-radius: 0.5rem;
    background: linear-gradient(45deg, #11a39c, #223a33);
    /* background-color: ${({ theme }) => theme.color.light};  */
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
    @media (max-width: 767px) {
      font-size:2rem;
    }
`;


export const PageTitleText=styled.h5`
  color: ${({ theme }) => theme.color.dark};
  /* color: ${({ theme }) => theme.color.light}; */
  margin:0%;
  padding-left:4rem;
  font-size:4rem;
  @media (max-width: 767px) {
    font-size:1.9rem;
    padding-left:2rem;
  }
`;

export const SmallPageTitleText=styled.h6`
  color: ${({ theme }) => theme.color.dark};
  /* color: ${({ theme }) => theme.color.light}; */
  margin:0%;
  font-size:2rem;
  @media (max-width: 767px) {
    font-size:1rem;
  }
`;
