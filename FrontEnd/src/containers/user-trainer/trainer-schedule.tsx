import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { api } from "@shared/common-data";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateCalendar } from "@mui/x-date-pickers/DateCalendar";
import dayjs, { Dayjs } from "dayjs";
import { Link } from "react-router-dom";
import { TrainerScheduleData } from "src/interface";

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

function TrainerSchedule() {
  const [value, setValue] = useState<Dayjs | null>(dayjs());
  const trainer = useSelector((state: RootState) => state.profile.trainerId);
  const selectedDate = value?.format("YYYY-MM-DD");
  const [items, setItems] = useState<TrainerScheduleData[]>([]);

  useEffect(() => {
    axios
      .get(`${api}/classes/${trainer}`)
      .then((response) => {
        console.log(response.data);
        setItems(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [trainer]);

  return (
    <Wrapper>
      <PageTitle>PT 수업 일정</PageTitle>
      <Link to="create_classes">수업 일정 생성하러가기</Link>
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
      {items.map(
        (item) =>
          item.startDate === selectedDate && (
            <ScheduleInfo key={item.classId}>
              <div>
                Class Id : {item.classId} Live Id : {item.liveId} Date :{" "}
                {item.startDate} Time : {item.startTime}
                Trainer ID : {item.trainerId}
              </div>
            </ScheduleInfo>
          )
      )}
    </Wrapper>
  );
}

export default TrainerSchedule;
