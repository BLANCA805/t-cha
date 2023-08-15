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
import { Link, useNavigate } from "react-router-dom";
import { TrainerScheduleData } from "src/interface";
import { GrayButton, TchaButton } from "@shared/button";
import WriteExerciseLog from "@user-trainer/write-exercise-log";

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
  flex-direction: row;
  margin-top: 5%;
  padding: 2%;
  border-radius: 10px;
  background-color: #fff;
`;

function TrainerSchedule() {
  const now = dayjs();
  const [value, setValue] = useState<Dayjs | null>(dayjs());
  const trainer = useSelector((state: RootState) => state.profile.trainerId);
  const selectedDate = value?.format("YYYY-MM-DD");
  const [items, setItems] = useState<TrainerScheduleData[]>([]);

  const navigate = useNavigate();

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

  const goToPtRoom = () => {
    navigate("/pt");
  };

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
        (item, index) =>
          item.startDate === selectedDate && (
            <ScheduleInfo key={index}>
              <div>
                Class Id : {item.classId} Live Id : {item.liveId} Date :{" "}
                {item.startDate} Time : {item.startTime}
                Trainer ID : {item.trainerId}
                <p>{item.startDate + item.startTime}</p>
              </div>
              {now.isAfter(
                dayjs(`${item.startDate} ${item.startTime}`).add(-5, "m")
              ) &&
                now.isBefore(
                  dayjs(`${item.startDate} ${item.startTime}`).add(60, "m")
                ) &&
                item.liveId && (
                  <TchaButton
                    onClick={goToPtRoom}
                    style={{ width: "8rem", color: "white" }}
                  >
                    PT 입장하기
                  </TchaButton>
                )}
              {item.liveId &&
                now.isAfter(
                  dayjs(`${item.startDate} ${item.startTime}`).add(60, "m")
                ) && <WriteExerciseLog liveId={item.liveId} />}
            </ScheduleInfo>
          )
      )}
    </Wrapper>
  );
}

export default TrainerSchedule;
