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
import { TrainerScheduleData } from "src/interface";
import { GrayButton, TchaButton, TchaButtonTextH6 } from "@shared/button";
import WriteExerciseLog from "@user-trainer/write-exercise-log";
import TrainerScheduleItem from "../trainer-schedule/trainer-schedule-item";
import CreateClasses from "./create-classes";
import { TitleWrapper, PageTitleText } from "@shared/page-title";
import { useNavigate } from "react-router-dom";

const Wrapper = styled.div`
  display: flex;
  width: 96%;
  flex-direction: column;
  min-height: 100vh;
  margin: 1% 0%;
`;

const ContentsWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  @media (max-width: 767px) {
    flex-direction: column;
    align-items: center;
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

  @media (max-width: 1470px) {
    height: 27rem;
    margin-right: 0%;
    margin-bottom: 2.5%;
    width: 100%;
    max-width: 100%;
    min-width: 0;
    justify-content: center;
  }
`;

const CalendarContainer = styled.div`
  display: flex;
  align-items: center;
  width: 20rem;
  height: 21rem;
  margin: 6% 0% 0% 0%;
  background-color: #ebebeb;
  border-radius: 10px;

  /* box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1); */
  box-shadow: 3px 3px 5px rgba(199, 177, 177, 0.1);
  @media (max-width: 767px) {
    margin-top: 3%;
    /* background-color: ${({ theme }) => theme.color.light}; */
    align-items: center;
    justify-content: center;
    width: 90%;
    height: 90%;
  }
`;

const CreatePtButton = styled(TchaButton)`
  height: 4rem;
  width: 20rem;
  margin-top: 6% !important;
  @media (max-width: 767px) {
    width: 94%;
    margin: 3% 0% !important;
  }
`;
const ScheduleInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 63%;
  min-width: 560px;
  min-height: 40rem;
  border-radius: 10px;
  overflow: hidden;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);

  @media (max-width: 767px) {
    width: 100%;
    min-width: 0;
    min-height: 0%;
    margin-top: 1%;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  }
`;

const ItemsInfoWrapper = styled.div`
  display: flex;
  /* justify-content: space-evenly; */
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 3rem;
  margin-bottom: 1.5%;
  background-color: ${({ theme }) => theme.color.primary};
  opacity: 80%;
`;
const StyledTextH6 = styled.h6`
  font-size: 1.3rem;
  margin: 0%;
  @media (max-width: 767px) {
    /* font-size:0.7rem; */
  }
`;

function TrainerSchedule() {
  const [date, setDate] = useState<Dayjs | null>(dayjs());
  const trainer = useSelector((state: RootState) => state.profile.trainerId);
  const selectedDate = date?.format("YYYY-MM-DD");
  const [items, setItems] = useState<TrainerScheduleData[]>([]);
  const [ptNum, setptNum] = useState(0);
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

  useEffect(() => {
    const count = items.filter(
      (item) => item.startDate === selectedDate
    ).length;
    setptNum(count);
  }, [selectedDate, items]);

  console.log(ptNum);

  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>PT 수업 일정</PageTitleText>
      </TitleWrapper>
      <ContentsWrapper>
        <CalendarWrapper>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <CalendarContainer>
              <DateCalendar
                value={date}
                onChange={(selected: Dayjs | null) => {
                  setDate(selected);
                }}
              />
            </CalendarContainer>
          </LocalizationProvider>
          <CreatePtButton onClick={() => navigate("create_classes")}>
            <TchaButtonTextH6 style={{ fontSize: "1.3rem" }}>
              수업 일정 생성하기
            </TchaButtonTextH6>
          </CreatePtButton>
        </CalendarWrapper>
        <ScheduleInfo>
          <ItemsInfoWrapper>
            <StyledTextH6 style={{ color: "white", margin: "0% 2%" }}>
              {selectedDate}
            </StyledTextH6>
            <StyledTextH6 style={{ color: "white", margin: "0% 2%" }}>
              예약 {ptNum}건
            </StyledTextH6>
          </ItemsInfoWrapper>
          {ptNum > 0 ? (
            items.map((item, index) => {
              if (item.startDate === selectedDate) {
                return <TrainerScheduleItem data={item} key={index} />;
              }
              return null;
            })
          ) : (
            <h6>해당 날짜에 예약이 없습니다</h6>
          )}
        </ScheduleInfo>
      </ContentsWrapper>
    </Wrapper>
  );
}

export default TrainerSchedule;
