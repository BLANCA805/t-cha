import DatePicker from "@shared/basic-date-picker";
import styled from "styled-components";
import UserScheduleList from "src/containers/user-schedule/user-schedule-list";
import UserScheduleListItem from "src/containers/user-schedule/user-schedule-list-item";

const Wrapper= styled.div`
  display:flex;
  flex-direction: column;
  height:100vh;
  margin: 1% 1%;
  `;

const PageTitle=styled.div`
  display:flex;
  height:10%;
  align-items: center;
  padding-left:2%;
  font-size:2rem;
  margin:1% 0%;
`
const Calendar= styled.div`
  margin-top:3%;
`
;
const ScheduleInfo= styled.div`
  display:flex;
  flex-direction: column;
  margin-top:5%;
  padding:2%;
  /* height:10rem; */
  border-radius: 10px;
  background-color: #fff;
`;


function UserSchedule() {
  return (
    <Wrapper>
      <PageTitle>
        내 캘린더
      </PageTitle>
      <Calendar>
        <DatePicker />
      </Calendar>
      <ScheduleInfo>
        <UserScheduleList /> 
      </ScheduleInfo>
    </Wrapper>
  );
}

export default UserSchedule;
