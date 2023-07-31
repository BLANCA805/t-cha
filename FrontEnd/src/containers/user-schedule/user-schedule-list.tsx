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
  