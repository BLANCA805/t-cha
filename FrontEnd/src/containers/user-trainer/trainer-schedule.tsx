import styled from "styled-components";
import { useEffect, useRef, useState } from "react";
import axios from "axios";
import { api } from "@shared/common-data";
import { useDispatch, useSelector } from "react-redux";
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

export interface PtLiveData {
  ptClassId: number;
  ptLiveId: number;
  status: string;
  trainerId: string;
  trainerName: string;
  trainerProfileImage: string;
  userId: string;
  userName: string;
  userProfileImage: string;
}

function TrainerSchedule() {
  const [value, setValue] = useState<Dayjs | null>(dayjs());
  const trainer = useSelector((state: RootState) => state.profile.trainerId);
  const selectedDate = value?.format("YYYY-MM-DD");
  const [items, setItems] = useState<TrainerScheduleData[]>([]);

  const navigate = useNavigate();

  // 오픈비두
  const [ptLiveData, setPtLiveData] = useState<PtLiveData>();

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


  const goToPtRoom = (ptLiveId: number) => {
    axios
      .get(`${api}/lives/${ptLiveId}`)
      .then((response) => {
        const ptLiveData:PtLiveData = response.data;

        // 트레이너 입장 가능 여부 확인
        if (ptLiveData.trainerId === trainer) {
          // if (ptLiveData?.status === "PROGRESS") {
          //   navigate("/pt", { state: ptLiveData });
          // } else {
          //   alert("입장 가능한 시간이 아닙니다");
          // }
          navigate("/pt", { state: ptLiveData });
        } else {
          alert("입장 권한이 없습니다");
        }
      })
      .catch((error) => {
        console.log(error);
      });
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
              </div>
              <TchaButton
                onClick={() => goToPtRoom(item.liveId)}
                style={{ width: "8rem", color: "white" }}
              >
                PT 입장하기
              </TchaButton>
              {item.liveId && item.status !== "INACCESSABLE" && (
                <WriteExerciseLog liveId={item.liveId} />
              )}
            </ScheduleInfo>
          )
      )}
    </Wrapper>
  );
}

export default TrainerSchedule;
