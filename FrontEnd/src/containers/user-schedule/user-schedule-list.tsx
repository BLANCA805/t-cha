import { useState, useEffect } from "react";
import axios from "axios";
import useAxios from "src/hooks/use-axios";
import UserScheduleListItem from "./user-schedule-list-item";

import styled from "styled-components";
import { isTemplateSpan } from "typescript";
import UserSchedule from "@/user-schedule";

const Wrapper = styled.div`

`;


function UserScheduleList() {
  //추가로 구현해야할 로직 
  //calendar 날짜 누르면 해당 날짜에 예약된, 현재 유저의 예약건들 전부 upload
  //data쪽
  //현재시점 기준으로 완료된 수업이면
    //붉은계열 색으로 칸 변경
    //버튼 리뷰쓰기로 바뀜 + 일지보기 버튼도?
  //입장 10분 전이면
    //버튼 BLACK PT방 입장으로 바뀜
  //아직 진행되지 않은 수업이면
    //예정이라는 GRAY버튼 


  // const [items, setItems] = useState<Array<any>>([]);
  // useEffect(() => {
  //   axios
  //     .get("http://localhost:8080/exercise-logs/{profileid}")
  //     .then((response) => {
  //       setItems(response.data);
  //       console.log(response);
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //     });
  // }, []);

  //using Temp dummy data 
  const items = [
    {
      trName: "이채림",
      ptName: "아침을 깨우는 유산소 운동",
      ptDate: "2023-05-05",
      ptStartTime: "10:00",
    },
    {
      trName: "변정원",
      ptName: "근력 운동과 올바른 자세",
      ptDate: "2023-10-02",
      ptStartTime: "13:00",
    },


  ];

  return (

    <Wrapper>
      {items.map((item) =>(
        <UserScheduleListItem data = {item}/>
      ))}

    </Wrapper>

  );
}
  
  export default UserScheduleList;
  