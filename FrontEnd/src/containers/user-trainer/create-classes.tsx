import React, { useEffect, useState } from "react";
import styled from "styled-components";

import { api } from "@shared/common-data";
import axios from "axios";

import Accordion from "@mui/material/Accordion";
import AccordionDetails from "@mui/material/AccordionDetails";
import AccordionSummary from "@mui/material/AccordionSummary";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { Button } from "@mui/material";
import { DateCalendar } from "@mui/x-date-pickers";
import { LocalizationProvider } from "@mui/x-date-pickers";
import dayjs, { Dayjs } from "dayjs";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  /* margin: 1% 1%; */
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
  margin: 3% 0%;
`;

const ContentsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-top: 3%;
  /* background-color: lightgray; */
`;

const ReservationWrapper = styled.div`
  width: 60%;
  margin-bottom: 3%;
`;
const StyledAccordion = styled(Accordion)`
  /* background-color: #e0e0e0 !important;  */
  /* background-color: ${({ theme }) => theme.color.primary} !important;  */
`;

const RegisterWrapper = styled.div`
  width: 100%;
  margin: 10% 0%;
`;
const RegisterButton = styled(Button)`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 3.3rem;
  width: 100%;
  background-color: #276e68 !important;
  /* background-color: ${({ theme }) => theme.color.primary}!important; */
  border-radius: 5px !important;
`;

function CreateClasses() {
  const initialDate = dayjs();
  const [selectedDate, setSelectedDate] = useState<Dayjs | null>(initialDate);
  const [expanded, setExpanded] = useState<string | false>(false);

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

  const trainerId = useSelector((state: RootState) => state.profile.trainerId);

  let startTimeList: string[] = [];

  const date = selectedDate?.format("YYYY-MM-DD");

  const body = {
    trainerId: trainerId,
    date: date,
    startTimeList: startTimeList,
  };

  const handleChange =
    (panel: string) => (event: React.SyntheticEvent, isExpanded: boolean) => {
      setExpanded(isExpanded ? panel : false);
    };

  const addTime = (event: string) => {
    startTimeList.push(event);
    console.log(startTimeList);
  };

  const createClass = () => {
    axios
      .post(`${api}/classes`, body)
      .then((response) => {
        console.log(response.data);
        startTimeList = [];
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Wrapper>
      <PageTitle>PT 일정 설정하기</PageTitle>
      <Calendar>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DateCalendar
            value={selectedDate}
            onChange={(selected) => setSelectedDate(selected)}
          />
        </LocalizationProvider>
      </Calendar>
      <ContentsWrapper>
        <ReservationWrapper>
          <Accordion>
            <AccordionSummary>
              <Typography sx={{ width: "60%" }}>날짜</Typography>
              <Typography>{date}</Typography>
            </AccordionSummary>
          </Accordion>
        </ReservationWrapper>

        <ReservationWrapper>
          <StyledAccordion
            expanded={expanded === "panel1"}
            onChange={handleChange("panel1")}
          >
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1bh-content"
              id="panel1bh-header"
            >
              <Typography sx={{ width: "63%", flexShrink: 0 }}>시간</Typography>
              <Typography sx={{ color: "text.secondary" }}>
                {" "}
                시간을 선택하세요{" "}
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {times.map((time, index) => (
                <button key={index} onClick={() => addTime(time)}>
                  {time}
                </button>
              ))}
            </AccordionDetails>
          </StyledAccordion>

          <RegisterWrapper>
            <RegisterButton variant="contained" onClick={createClass}>
              <Typography variant="h6">PT 일정 설정하기</Typography>
            </RegisterButton>
          </RegisterWrapper>
        </ReservationWrapper>
      </ContentsWrapper>
    </Wrapper>
  );
}

export default CreateClasses;
