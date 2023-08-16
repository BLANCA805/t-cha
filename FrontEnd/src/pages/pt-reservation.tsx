import React, { useEffect, useState } from "react";
import styled from "styled-components";

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
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { api } from "@shared/common-data";
import { PtClassDataProps } from "src/interface";
import { color } from "@mui/system";

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

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function PtReservation() {
  const trainer = useLocation().state;
  const user = useSelector((state: RootState) => state.profile.id);
  const initialDate = dayjs();
  const [selectedDate, setSelectedDate] = useState<Dayjs | null>(initialDate);
  const [ptClassData, setPtClassData] = useState<PtClassDataProps[]>([]);
  const [selectedClassData, setSelectedClassData] =
    useState<PtClassDataProps>();

  let date = selectedDate?.format("YY-MM-DD");

  const navigate = useNavigate();

  const form = {
    userId: user,
    ptClassId: selectedClassData?.classId,
  };

  useEffect(() => {
    axios
      .get(`${api}/classes/${trainer}`)
      .then((response) => {
        setPtClassData(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  });

  const reserveClass = () => {
    console.log(form);
    axios
      .patch(`${api}/classes`, form)
      .then((response) => {
        console.log(response.data);
        navigate("/profile/schedule");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const selectClass = (data: PtClassDataProps) => {
    console.log(data);
    if (selectedClassData?.classId === data.classId) {
      setSelectedClassData(undefined);
    } else {
      setSelectedClassData(data);
    }
  };

  return (
    <Wrapper>
      <PageTitle>예약하기</PageTitle>
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
          <div>
            <Typography sx={{ width: "63%", flexShrink: 0 }}>시간</Typography>

            {ptClassData?.map(
              (data) =>
                selectedDate?.format("YYYY-MM-DD") === data.startDate && (
                  <Button
                    variant={
                      selectedClassData?.classId === data.classId
                        ? "outlined"
                        : "contained"
                    }
                    disabled={!!data?.liveId}
                    style={{ margin: "0.4rem 0.5rem" }}
                    onClick={() => selectClass(data)}
                    key={data.classId}
                  >
                    <Typography variant="h5">
                      {data.startTime.slice(0, 5)}
                    </Typography>
                  </Button>
                )
            )}
          </div>

          <RegisterWrapper>
            <div>
              <h2>** 주의사항 **</h2>
              <p>예약 확정 전 반드시 읽어주시기 바랍니다.</p>
              <p>
                1. 예약 확정 이후 트레이너께서 확인하시면 결제 알림 메시지가
                보내지며, 수락 시 등록된 카드로 결제가 진행됩니다.
              </p>
              <p>2. PT 기본 시간은 50분 정도입니다.</p>
              <p>
                3. 쌍방 예약 확정 이후에는 일정 변경이 번거로울 수 있으니
                예약정보를 반드시 다시 확인한 후 예약을 확정해 주세요.
              </p>
            </div>
          </RegisterWrapper>

          <RegisterWrapper>
            <RegisterButton onClick={reserveClass} variant="contained">
              <Typography variant="h6">예약 및 결제하기</Typography>
            </RegisterButton>
          </RegisterWrapper>
        </ReservationWrapper>
      </ContentsWrapper>
    </Wrapper>
  );
}

export default PtReservation;
