import { useEffect, useState } from "react";
import styled from "styled-components";

import { api } from "@shared/common-data";
import axios from "axios";

import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import Typography from "@mui/material/Typography";
import { Button } from "@mui/material";
import { DateCalendar } from "@mui/x-date-pickers";
import { LocalizationProvider } from "@mui/x-date-pickers";
import dayjs, { Dayjs } from "dayjs";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import TransferList from "@shared/transfer-list";
import { useNavigate } from "react-router-dom";
import { TitleWrapper, PageTitleText, SmallTitleWrapper } from "@shared/page-title";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width:97%;
  height:100%;
`;

const Calendar = styled.div`
  display:flex;
  margin: 3% 0%;
  width:20rem;
  height:21rem;
  background-color: white;
  border-radius: 10px;
`;

const CalendarWrapper = styled.div`
  display: flex;
  justify-content: center;
  /* align-items: center; */
  margin-right:2%;
  width: 40%;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    width:100%;
    max-width:100%;
    margin-right:0%;
    justify-content: center;
  }
`

const ContentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 70%;
  min-height: 40rem;
  padding: 1% 0%;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    width: 100%;
    padding:2% 0%;
    min-height: 40%;
    margin-top: 3%;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  }
  /* display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-top: 3%; */
`;



const ReservationWrapper = styled.div`
  width: 60%;
  margin-bottom: 3%;
  background-color: pink;
  @media (max-width: 767px) {
    width:96%;
    /* box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1); */
  }
`;
const ReservCreateWrapper = styled.div`
  width: 60%;
  margin-bottom: 3%;
  background-color: #bd3d52;
  @media (max-width: 767px) {
    width:96%;
    /* box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1); */
  }
`;

const RegisterWrapper = styled.div`
  width: 100%;
  margin: 10% 0%;
  background-color: magenta;
`;
const RegisterButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 3.3rem;
  width: 100%;
  background-color: #276e68 !important;
  border-radius: 5px !important;
`;

interface CreatedData {
  date: string;
  time: string;
}

function CreateClasses() {
  const trainerId = useSelector((state: RootState) => state.profile.trainerId);
  const navigate = useNavigate();
  const initialDate = dayjs();
  const [selectedDate, setSelectedDate] = useState<Dayjs | null>(initialDate);
  const [created, setCreated] = useState<CreatedData[]>([]);

  const [startTimeList, setStartTimeList] = useState<string[]>([]);

  const date = selectedDate?.format("YYYY-MM-DD");

  useEffect(() => {
    axios.get(`${api}/classes/${trainerId}`).then((response) => {
      console.log(response.data);
      for (let i in response.data) {
        setCreated([
          ...created,
          {
            date: response.data[i].startDate,
            time: response.data[i].startTime,
          },
        ]);
      }
    });
  }, []);

  const times = [
    "00:00",
    "00:30",
    "01:00",
    "01:30",
    "02:00",
    "02:30",
    "03:00",
    "03:30",
    "04:00",
    "04:30",
    "05:00",
    "05:30",
    "06:00",
    "06:30",
    "07:00",
    "07:30",
    "08:00",
    "08:30",
    "09:00",
    "09:30",
    "10:00",
    "10:30",
    "11:00",
    "11:30",
    "12:00",
    "12:30",
    "13:00",
    "13:30",
    "14:00",
    "14:30",
    "15:00",
    "15:30",
    "16:00",
    "16:30",
    "17:00",
    "17:30",
    "18:00",
    "18:30",
    "19:00",
    "19:30",
    "20:00",
    "20:30",
    "21:00",
    "21:30",
    "22:00",
    "22:30",
    "23:00",
    "23:30",
  ];

  const filteredTimes = times.filter((time) => {
    const [hours, minutes] = time.split(":");
    const timeToCheck = dayjs()
      .set("hour", parseInt(hours))
      .set("minute", parseInt(minutes));
    return timeToCheck.isAfter(dayjs());
  });

  const body = {
    trainerId: trainerId,
    date: date,
    startTimeList: startTimeList,
  };

  const createClass = () => {
    axios
      .post(`${api}/classes`, body)
      .then((response) => {
        console.log(response.data);
        navigate("/profile/trainer_schedule");
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleChangeList = (items: string[]) => {
    setStartTimeList(items);
  };

  const test = () => {
    console.log(created);
  };

  return (
    <Wrapper>
      <button onClick={test}>Test</button>
      <TitleWrapper><PageTitleText>PT 일정 설정하기</PageTitleText></TitleWrapper>
      <CalendarWrapper>
        <Calendar>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DateCalendar
              value={selectedDate}
              onChange={(selected) => setSelectedDate(selected)}
            />
          </LocalizationProvider>
        </Calendar>
      </CalendarWrapper>
      <ContentsWrapper>
        <ReservationWrapper>
          <Accordion>
            <AccordionSummary>
              <Typography sx={{ width: "60%" }}>날짜</Typography>
              <Typography>{date}</Typography>
            </AccordionSummary>
          </Accordion>
        </ReservationWrapper>
        {selectedDate?.isAfter(dayjs().subtract(1, "day")) && (
          <ReservCreateWrapper>
            <TransferList
              times={
                dayjs().format("YYYY-MM-DD") === date ? filteredTimes : times
              }
              handleChangeList={handleChangeList}
            />
            <RegisterWrapper>
              <RegisterButton variant="contained" onClick={createClass}>
                <Typography variant="h6">PT 일정 설정하기</Typography>
              </RegisterButton>
            </RegisterWrapper>
          </ReservCreateWrapper>
        )}
        {!selectedDate?.isAfter(dayjs().subtract(1, "day")) && (
          <p>지난 날짜는 선택할 수 없습니다</p>
        )}
      </ContentsWrapper>
    </Wrapper>
  );
}

export default CreateClasses;
