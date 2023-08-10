import styled from "styled-components";
import UserScheduleItem from "src/containers/user-schedule/user-schedule-item";
import { useEffect, useState } from "react";
import axios from "axios";
import { api } from "@shared/common-data";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateCalendar } from "@mui/x-date-pickers/DateCalendar";
import dayjs, { Dayjs } from "dayjs";

const CalendarContainer = styled.div`
  background-color: white;
  border-radius: 10px;
  width: 100%;
  display: flex;
  align-items: center;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  margin: 1% 1%;
`;

const PageTitle = styled.div`
  display: flex;
  height: 10%;
  align-items: center;
  padding-left: 2%;
  font-size: 2rem;
  margin: 1% 0%;
`;
const Calendar = styled.div`
  margin-top: 3%;
`;
const ScheduleInfo = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 5%;
  padding: 2%;
  /* height:10rem; */
  border-radius: 10px;
  background-color: #fff;
`;

function UserSchedule() {
  const [value, setValue] = useState<Dayjs | null>(dayjs());
  const user = useSelector((state: RootState) => state.profile.profileId);

  useEffect(() => {
    axios
      .get(`${api}/classes/user/${user}`)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [user]);

  const selectedDate = value?.format("YYYY-MM-DD");

  console.log(selectedDate);

  return (
    <Wrapper>
      <PageTitle>내 캘린더</PageTitle>
      <Calendar>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <CalendarContainer>
            <DateCalendar
              value={value}
              onChange={(selected: Dayjs | null) => setValue(selected)}
            />
          </CalendarContainer>
        </LocalizationProvider>
      </Calendar>
      <ScheduleInfo>{/* <UserScheduleItem /> */}</ScheduleInfo>
    </Wrapper>
  );
}

export default UserSchedule;
