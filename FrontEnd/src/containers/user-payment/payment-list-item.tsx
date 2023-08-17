

import styled from "styled-components"

const Wrapper = styled.div`
  width:100%;
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
  margin-bottom: 0.5%;
  height:12rem;
  @media (max-width: 767px) {
   height:6rem; 
   border-radius: 5px;
  }
`;

const ContentsWrapper = styled.div`
  display:flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width:100%;
  /* padding:1%; */
`;

const TopContainer = styled.div`
  flex:3;
  display: flex;
  width:100%;
  flex-direction: row;
  justify-content: space-between ;
  border-bottom: 0.5px solid lightgrey;
  /* margin-left:5%;
  margin-right:5%; */
  align-items:center;
`;

const BottomContainer = styled.div`
  flex:9;
  display: flex;
  width:100%;
  flex-direction: row;
  justify-content: center;
`;

const LeftContainer = styled.div`
  flex:3;
  display:flex;
  flex-direction: column;
  justify-content: center;
  align-items: start;
`;

const RightContainer = styled.div`
  flex:9;
  display:flex;
  flex-direction: column;
  align-items:start;
  justify-content: center;

`;

const PTInfo = styled.div`
  display:flex;
  align-items:center;
  padding-left:2rem;
  padding-bottom:0.6rem;
  width:100%;
`;

const TrainerInfo = styled.div`
  display: flex;
  align-items: center;
  width:100%;
  padding-left:2rem;
  /* padding-bottom:1rem; */
`;


const StyledTextH6 = styled.h6`
  margin:0%;
  font-size:1rem;
  @media (max-width: 767px) {
    font-size:0.7rem;
  }
`
const StyledTextH5 = styled.h5`
  margin:0%;
  font-size:1.2rem;
  @media (max-width: 767px) {
    font-size:0.8rem;
  }
`
const StyledTextH4 = styled.h4`
  margin:0% 0% 1% 0%;
  font-size:1.5rem;
  @media (max-width: 767px) {
    font-size:1rem;
  }
`

interface PaymentListItemProps{
  data:{
    //must be changed 
    paydate:string;
    ptdate:string;
    pttime:string;
    trname:string;
    ptname:string;
    price:string;
  };
}


function PaymentListItem(props: PaymentListItemProps){

  return (
    <Wrapper>
      <ContentsWrapper>

        <TopContainer>
          <StyledTextH6 style={{marginLeft:"3%"}}>결제일: {props.data.paydate}</StyledTextH6>
          <StyledTextH6 style={{marginRight:"3%"}}>{props.data.price}원</StyledTextH6>
        </TopContainer>
        
        <BottomContainer>
          <LeftContainer>
            <StyledTextH6 style={{marginLeft:"20%"}}>{props.data.ptdate}</StyledTextH6>
            <StyledTextH6 style={{marginTop:"3%", marginLeft:"20%"}}>{props.data.pttime}</StyledTextH6>
          </LeftContainer>
          <RightContainer>
            <PTInfo>
              <StyledTextH4>
                {props.data.ptname}
              </StyledTextH4>
            </PTInfo>
            <TrainerInfo>
              <StyledTextH6>트레이너</StyledTextH6>
              <StyledTextH5 style={{paddingLeft:"0.75rem"}}>{props.data.trname}</StyledTextH5>
            </TrainerInfo>
          </RightContainer>
        </BottomContainer>

      </ContentsWrapper>
    </Wrapper>
    
    
  );
}

export default PaymentListItem