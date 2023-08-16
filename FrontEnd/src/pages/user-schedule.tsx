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

import { TitleWrapper, PageTitleText } from "@shared/page-title";


const Wrapper = styled.div`
  display: flex;
  width: 96%;
  flex-direction: column;
  height: 100vh;
  margin: 1% 1% 0% 1%;
`;

const CalendarContainer = styled.div`
  background-color: white;
  border-radius: 10px;
  width: 100%;
  height:20rem;
  display: flex;
  align-items: center;
  /* padding: 1% 0%; */
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
  }
`;

const Calendar = styled.div`

`;

const ScheduleInfo = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 1%;
  padding: 2%;
  /* height:10rem; */
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
  @media (max-width: 767px) {
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

  console.log(selectedDate);

  return (
    <Wrapper>
      <TitleWrapper>
        <PageTitleText>내 캘린더</PageTitleText>
      </TitleWrapper>
      <Calendar>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <CalendarContainer>
            <DateCalendar
              value={date}
              onChange={(selected: Dayjs | null) => setDate(selected)}
              sx={{
                "& .MuiDataGrid-root": {
                  height: "400px",
                  width: "400px",
                },
              }}
            />
          </CalendarContainer>
        </LocalizationProvider>
      </Calendar>
      <ScheduleInfo>
        {items[0] ? (
          items.map((item) => <UserScheduleItem data={item} />)
        ) : (
          <div style={{ display: "flex", justifyContent: "center" }}>
            <p>해당 날짜에 예약이 없습니다</p>
          </div>
        )}
      </ScheduleInfo>
    </Wrapper>
  );
}

export default UserSchedule;
