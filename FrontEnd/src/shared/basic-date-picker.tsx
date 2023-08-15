import styled from "styled-components";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateCalendar } from "@mui/x-date-pickers/DateCalendar";
import { useState } from "react";
import dayjs from "dayjs";

const StyledCalendar = styled(DateCalendar)`
  background-color: white;
  border-radius: 10px;
`;

function DatePicker() {
  const [selectDate, setSelectDate] = useState()
  const date = dayjs(selectDate).format("YYYY-MM-DD");
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <StyledCalendar
        value={selectDate}
        onChange={(newValue: any) => {
          setSelectDate(newValue)
        }}
      />
    </LocalizationProvider>
  );
}



export default DatePicker;