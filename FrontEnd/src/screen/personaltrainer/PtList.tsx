import { Link } from "react-router-dom";

import styled from "styled-components";

import { TextField } from "@mui/material";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

import Button from "@mui/material/Button";

import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDateTimePicker } from "@mui/x-date-pickers/StaticDateTimePicker";

import PtListItem from "./PtListItem";

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

function PtList() {
  return (
    <div>
      <Wrapper>
        <TextField
          id="outlined-basic"
          label="키워드 검색"
          variant="outlined"
          style={{ width: "100%", marginBottom: "1%" }}
        />
        <ButtonContainer>
          <Button size="large" variant="outlined">
            Outlined
          </Button>
          <Button size="large" variant="outlined">
            Outlined
          </Button>
          <Button size="large" variant="outlined">
            Outlined
          </Button>
          <Button size="large" variant="outlined">
            Outlined
          </Button>
          <Button size="large" variant="outlined">
            Outlined
          </Button>
        </ButtonContainer>
        <Accordion>
          <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1a-content"
            id="panel1a-header"
          >
            <Typography>Accordion 1</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <StaticDateTimePicker orientation="landscape" />
            </LocalizationProvider>
          </AccordionDetails>
        </Accordion>
      </Wrapper>
      <PtListItem />
    </div>
  );
}

export default PtList;
