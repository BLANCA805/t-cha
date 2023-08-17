import { useState, useEffect } from "react";
import axios from "axios";
import PaymentListItem from "./payment-list-item";

import styled from "styled-components";


const Wrapper=styled.div`
  display:flex;
  flex-direction: column;
  width:99%;
  align-items: center;
  /* margin: 3%; */
  height:100vh;
`;

function PaymentList(){

  const items = [
    {
      paydate:"2023-02-25",
      ptdate:"2023-03-05",
      pttime:"13:00 - 14:00",
      trname:"변정원",
      ptname:"근력 운동과 올바른 자세",
      price:"50,000",
    },
    {
      paydate:"2023-01-20",
      ptdate:"2023-02-23",
      pttime:"14:00 - 15:00",
      trname:"이채림",
      ptname:"아침을 깨우는 유산소 운동",
      price:"45,000",
    },
    {
      paydate:"2023-02-25",
      ptdate:"2023-02-05",
      pttime:"17:00 - 18:00",
      trname:"최해미",
      ptname:"필라테스와 자세 교정",
      price:"48,000",
    },


  ]

  return (
    <Wrapper>
      {items.map((item) => (
          <PaymentListItem data = {item}/>
      ))}
    </Wrapper>
  );

}

export default PaymentList