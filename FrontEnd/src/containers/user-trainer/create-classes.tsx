import { useEffect, useState } from "react";
import { useMediaQuery } from "react-responsive";
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
import {
  TitleWrapper,
  PageTitleText,
  SmallTitleWrapper,
} from "@shared/page-title";
import {
  TchaButton,
  TchaButtonTextH6,
  ColoredButtonText,
} from "@shared/button";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 96%;
  /* width:97%; */
  margin: 1% 0%;
  height: 100%;
`;

const ReservationWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  @media (max-width: 767px) {
    flex-direction: column;
  }
`;

const CalendarWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  margin-right: 2%;
  width: 35%;
  min-width: 23.5rem;
  height: 40rem;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);

  @media (max-width: 1450px) {
    height: 23.5rem;
    margin-bottom: 2.5%;
  }
  @media (max-width: 767px) {
    width: 100%;
    max-width: 100%;
    min-width: 0;
    margin-right: 0%;
    justify-content: center;
  }
`;

const Calendar = styled.div`
  display: flex;
  align-items: center;
  width: 20rem;
  height: 21rem;
  margin: 6% 0%;
  background-color: #ebebeb;
  border-radius: 10px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    margin: 3% 0%;
    align-items: center;
    justify-content: center;
    width: 90%;
    height: 90%;
  }
`;
const ContentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 63%;
  min-width: 560px;
  height: 40rem;
  /* padding: 0% 2%; */
  border-radius: 10px;
  overflow: hidden;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);

  @media (max-width: 1450px) {
    height: 35rem;
    margin-bottom: 2.5%;
  }
  @media (max-width: 767px) {
    width: 100%;
    min-width: 0;
    min-height: 0%;
    height: 33rem;
    /* padding:4% 0% 0% 0%; */
    /* justify-content: center; */
    margin-top: 1%;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  }
`;

const DateWrapper = styled.div`
  /* border-top-right-radius: 5px; */
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 8%;
  width: 100%;
  min-width: 520px;
  /* margin: 3% 0% 0% 0%; */
  /* background-color: #6e8582; */
  background-color: ${({ theme }) => theme.color.primary};
  opacity: 80%;
`;
const ReservCreateWrapper = styled.div`
  width: 60%;
  min-width: 520px;
  margin-bottom: 3%;
  @media (max-width: 767px) {
    width: 96%;
    min-width: 0;
  }
`;

const RegisterWrapper = styled.div`
  width: 100%;
  margin: 15% 0%;
`;
const RegisterButton = styled(TchaButton)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 3.3rem;
  width: 98%;
  background-color: #276e68 !important;
  border-radius: 5px !important;
`;

const StyledTextH6 = styled.h6`
  font-size: 2.5rem;
  /* color:${({ theme }) => theme.color.tcha}; */
  /* color:gray; */
  /* color:#4982d8; */
  color: #2d9178;
  @media (max-width: 767px) {
    /* font-size:0.7rem; */
  }
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

  const DisplayWide = useMediaQuery({ minWidth: 1451 });
  const DisplaySmall = useMediaQuery({ minWidth: 1450 });

  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>PT 일정 설정하기</PageTitleText>
      </TitleWrapper>
      <ReservationWrapper>
        <CalendarWrapper>
          <Calendar>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DateCalendar
                value={selectedDate}
                onChange={(selected) => setSelectedDate(selected)}
              />
            </LocalizationProvider>
          </Calendar>
          {DisplayWide && (
            <StyledTextH6 style={{ marginTop: "50px" }}>{date}</StyledTextH6>
          )}
        </CalendarWrapper>
        <ContentsWrapper>
          {!DisplayWide && (
            <DateWrapper>
              <StyledTextH6
                style={{ margin: "0%", fontSize: "1.1rem", color: "white" }}
              >
                {date}
              </StyledTextH6>
            </DateWrapper>
          )}
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
                  <TchaButtonTextH6 style={{ fontSize: "1.2rem" }}>
                    PT 일정 설정하기
                  </TchaButtonTextH6>
                </RegisterButton>
              </RegisterWrapper>
            </ReservCreateWrapper>
          )}
          {!selectedDate?.isAfter(dayjs().subtract(1, "day")) && (
            <h6>지난 날짜는 선택할 수 없습니다</h6>
          )}
        </ContentsWrapper>
      </ReservationWrapper>
    </Wrapper>
  );
}

export default CreateClasses;
