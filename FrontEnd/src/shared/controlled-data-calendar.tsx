import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateCalendar } from "@mui/x-date-pickers/DateCalendar";
import dayjs, { Dayjs } from "dayjs";

import { useState } from "react";
import { DemoContainer, DemoItem } from "@mui/x-date-pickers/internals/demo";

import styled from "styled-components";

const Container = styled.div`
  background-color: white;
  border-radius: 10px;
  width: 100%;
  display: flex;
  align-items: center;
`;

function DatePicker() {
  const [value, setValue] = useState<Dayjs | null>(dayjs());

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Container>
        <DateCalendar
          value={value}
          onChange={(selected: Dayjs | null) => setValue(selected)}
        />
      </Container>
    </LocalizationProvider>
  );
}

export default DatePicker;
