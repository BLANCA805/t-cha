import styled from "styled-components";

import { TextField } from "@mui/material";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

import Button from "@mui/material/Button";

import DatePicker from "../../component/DatePicker";

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
  const trainerListData = [
    {
      id: 1,
      name: "임병국1",
      keywordTags: ["야생", "강인함", "상남자"],
      registrationDate: "2023-07-25",
      studentCount: 777,
      reservationCount: 1000,
      reviewCount: 850,
      reReservationRate: 98,
    },
    {
      id: 2,
      name: "임병국2",
      keywordTags: ["야생", "강인함", "상남자"],
      registrationDate: "2023-07-25",
      studentCount: 777,
      reservationCount: 1000,
      reviewCount: 850,
      reReservationRate: 98,
    },
    {
      id: 3,
      name: "임병국3",
      keywordTags: ["야생", "강인함", "상남자"],
      registrationDate: "2023-07-25",
      studentCount: 777,
      reservationCount: 1000,
      reviewCount: 850,
      reReservationRate: 98,
    },
  ];
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
            <DatePicker />
          </AccordionDetails>
        </Accordion>
      </Wrapper>
      <div>
        {trainerListData.map((trainerData, index) => (
          <PtListItem data={trainerData} />
        ))}
      </div>
    </div>
  );
}

export default PtList;
