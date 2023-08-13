import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDateTimePicker } from "@mui/x-date-pickers/StaticDateTimePicker";
import { MobileDateTimePicker } from '@mui/x-date-pickers/MobileDateTimePicker';

export function DateTimePicker() {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <StaticDateTimePicker orientation="landscape" />
    </LocalizationProvider>
  );
}
export function SmDateTimePicker() {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <MobileDateTimePicker/>
    </LocalizationProvider>
  );
}

export default DateTimePicker;
