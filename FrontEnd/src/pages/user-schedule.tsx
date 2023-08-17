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
import { UserScheduleData } from "src/interface";
import Calendar from "react-calendar";
import { TitleWrapper, PageTitleText } from "@shared/page-title";

const Wrapper = styled.div`
  display: flex;
  width: 96%;
  flex-direction: column;
  min-height: 100vh;
  margin: 1% 0%;
`;

const ContentsWrapper = styled.div`
  display: flex;
  width: 100%;
  @media (max-width: 767px) {
    flex-direction: column;
  }
`;

const CalendarWrapper = styled.div`
  display: flex;
  justify-content: center;
  /* align-items: center; */
  margin-right: 2%;
  width: 40%;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    width: 100%;
    max-width: 100%;
    margin-right: 0%;
    justify-content: center;
  }
`;

const CalendarContainer = styled.div`
  display: flex;
  align-items: center;
  background-color: #ebebeb;
  border-radius: 10px;
  width: 20rem;
  height: 21rem;
  margin-top: 5%;
  /* padding: 1% 0%; */
  /* box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1); */
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    margin-top: 3%;
    background-color: ${({ theme }) => theme.color.light};
    align-items: center;
    justify-content: center;
    width: 90%;
    height: 90%;
  }
`;

const ScheduleInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 70%;
  min-height: 40rem;
  padding: 1% 0%;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.color.light};
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
    width: 100%;
    padding: 2% 0%;
    min-height: 40%;
    margin-top: 3%;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  }
`;

function UserSchedule() {
  const [date, setDate] = useState<Dayjs | null>(dayjs());
  const user = useSelector((state: RootState) => state.profile.profileId);
  const [items, setItems] = useState<UserScheduleData[]>([]);

  useEffect(() => {
    axios
      .get(`${api}/classes/user/${user}`)
      .then((response) => {
        console.log(response.data);
        setItems(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [user]);

  const selectedDate = date?.format("YYYY-MM-DD");

  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>내 캘린더</PageTitleText>
      </TitleWrapper>
      <ContentsWrapper>
        <CalendarWrapper>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <CalendarContainer>
              <DateCalendar
                value={date}
                onChange={(selected: Dayjs | null) => setDate(selected)}
              />
            </CalendarContainer>
          </LocalizationProvider>
        </CalendarWrapper>
        <ScheduleInfo>
          {items[0] ? (
            items.map((item, index) => (
              <UserScheduleItem data={item} key={index} />
            ))
          ) : (
            <div style={{ display: "flex", justifyContent: "center" }}>
              <p>해당 날짜에 예약이 없습니다</p>
            </div>
          )}
        </ScheduleInfo>
      </ContentsWrapper>
    </Wrapper>
  );
}

export default UserSchedule;
