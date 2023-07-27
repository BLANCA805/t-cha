import DateTimePicker from "@shared/date-time-picker";

import { TextField } from "@mui/material";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Button from "@mui/material/Button";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: white;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 1%;
`;

function TrainerListHeader() {
  return (
    <Wrapper>
      <TextField
        id="outlined-basic"
        label="키워드 검색"
        variant="outlined"
        style={{ width: "100%", marginBottom: "1%" }}
      />
      <ButtonContainer>
        <Button size="large" variant="outlined">
          정렬 조건 1
        </Button>
        <Button size="large" variant="outlined">
          정렬 조건 2
        </Button>
        <Button size="large" variant="outlined">
          정렬 조건 3
        </Button>
        <Button size="large" variant="outlined">
          정렬 조건 4
        </Button>
        <Button size="large" variant="outlined">
          정렬 조건 5
        </Button>
      </ButtonContainer>
      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1a-content"
          id="panel1a-header"
        >
          <Typography>날짜 및 시간 선택</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <DateTimePicker />
        </AccordionDetails>
      </Accordion>
    </Wrapper>
  );
}

export default TrainerListHeader;
