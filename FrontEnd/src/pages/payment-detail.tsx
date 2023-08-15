
import { ColorTitleWrapper,TitleWrapper, PageTitleText } from "@shared/page-title";
import PaymentList from "src/containers/user-payment/payment-list";

import styled from "styled-components";


const Wrapper = styled.div`
  display:flex;
  flex-direction: column;
  width:97%;
  margin: 1.5% 0%;
  height:100vh;
`;



function PaymentDetail() {
  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText> 결제내역 </PageTitleText>
      </TitleWrapper>
      <PaymentList/>
    </Wrapper>
  );
}

export default PaymentDetail;
