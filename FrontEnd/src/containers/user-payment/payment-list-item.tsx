

import styled from "styled-components"

const Wrapper = styled.div`
  background-color: ${({ theme }) => theme.color.light};
  border-radius: 10px;
  margin-bottom: 0.5%;
  height:12rem;
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
  align-items: center;
`;

const RightContainer = styled.div`
  flex:9;
  display:flex;
  flex-direction: column;
  align-items:start;
  justify-content: center;

`;

const PTInfo = styled.div`
  padding-left:2rem;
  padding-bottom:0.6rem;
`;

const TrainerInfo = styled.div`
  padding-left:2rem;
  padding-bottom:1rem;
`;

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
          <p style={{marginLeft:"3%"}}>결제일: {props.data.paydate}</p>
          <p style={{marginRight:"3%"}}>{props.data.price}원</p>
        </TopContainer>
        
        <BottomContainer>
          <LeftContainer>
            <b style={{fontSize:"1.25rem"}}>{props.data.ptdate}</b>
            <b style={{fontSize:"1rem", paddingBottom:"1rem"}}>{props.data.pttime}</b>
          </LeftContainer>
          <RightContainer>
            <PTInfo>
              <b style={{fontSize:"2rem"}}>
                {props.data.ptname}
              </b>
            </PTInfo>
            <TrainerInfo>
              <b style={{fontSize:"1rem"}}>트레이너</b>
              <b style={{fontSize:"1.35rem", paddingLeft:"0.75rem"}}>{props.data.trname}</b>
            </TrainerInfo>
          </RightContainer>
        </BottomContainer>

      </ContentsWrapper>
    </Wrapper>
    
    
  );
}

export default PaymentListItem