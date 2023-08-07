import React from "react";
import DatePicker from "@shared/basic-date-picker";
import styled from "styled-components";


import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {Button} from "@mui/material";

const Wrapper= styled.div`
  display:flex;
  flex-direction: column;
  height:100%;
  /* margin: 1% 1%; */
  `;

const PageTitle=styled.div`
  display:flex;
  height:10%;
  align-items: center;
  padding-left:2%;
  font-size:2rem;
  margin:1% 0%;
`
const Calendar= styled.div`
  margin:3% 0%;
  `

const ContentsWrapper= styled.div`
  display:flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width:100%;
  margin-top:3%;
  /* background-color: lightgray; */
`;

const ReservationWrapper = styled.div`  
  width: 60%;
  margin-bottom:3%;
`
const StyledAccordion = styled(Accordion)`
  /* background-color: #e0e0e0 !important;  */
  /* background-color: ${({ theme }) => theme.color.primary} !important;  */
  `;

const RegisterWrapper = styled.div`  
  width: 100%;
  margin:10% 0%;
`
const RegisterButton = styled(Button)`
  display:flex;
  justify-content: center;
  align-items: center;
  height:3.3rem;
  width:100%;
  background-color: #276e68!important;
  /* background-color: ${({ theme }) => theme.color.primary}!important; */
  border-radius: 5px !important;
  `;


function PtReservation(){
  const [expanded, setExpanded] = React.useState<string | false>(false);

  const handleChange =
    (panel: string) => (event: React.SyntheticEvent, isExpanded: boolean) => {
      setExpanded(isExpanded ? panel : false);
    };

  return (
    <Wrapper>
      <PageTitle>
        예약하기
      </PageTitle>
      <Calendar>
        <DatePicker />
      </Calendar>
      {/* <ContentsWrapper>
        날짜와 시간을 선택해 주세요.
      </ContentsWrapper> */}
      <ContentsWrapper>
        <ReservationWrapper>

          <Accordion>
            <AccordionSummary>
              <Typography sx={{ width: '60%'}}>
                날짜
              </Typography>
              <Typography sx={{color: 'text.secondary' }}> dummy 2023-d-31 </Typography>
            </AccordionSummary>
          </Accordion>
        </ReservationWrapper>

        <ReservationWrapper>
          <StyledAccordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1bh-content"
              id="panel1bh-header"
            >
              <Typography sx={{ width: '63%', flexShrink: 0 }}>
                시간
              </Typography>
              <Typography sx={{ color: 'text.secondary' }}> 시간을 선택하세요 </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography>
                {/* Nulla facilisi. Phasg quam. */}
              </Typography>
              <Button variant="contained" style={{margin: '0.4rem 0.5rem'}}>
               <Typography variant="h5">12:21</Typography>
              </Button> 
              <Button variant="contained" style={{margin: '0.4rem 0.5rem'}}>
               <Typography variant="h5">12:21</Typography>
              </Button> 
              <Button variant="contained" style={{margin: '0.4rem 0.5rem'}}>
               <Typography variant="h5">12:21</Typography>
              </Button> 
              <Button variant="contained" style={{margin: '0.4rem 0.5rem'}}>
               <Typography variant="h5">12:21</Typography>
              </Button> 
              <Button variant="contained" style={{margin: '0.4rem 0.5rem'}}>
               <Typography variant="h5">12:21</Typography>
              </Button> 
              <Button variant="contained" style={{margin: '0.4rem 0.5rem'}}>
               <Typography variant="h5">12:21</Typography>
              </Button> 
              <Button variant="contained" style={{margin: '0.4rem 0.5rem'}}>
               <Typography variant="h5">12:21</Typography>
              </Button> 

            </AccordionDetails>
          </StyledAccordion>

          <RegisterWrapper>
            <Typography>
              <h2>** 주의사항 **</h2>
              <p>예약 확정 전 반드시 읽어주시기 바랍니다.</p>
              <p>1. 예약 확정 이후 트레이너께서 확인하시면 결제 알림 메시지가 보내지며, 
                수락 시 등록된 카드로 결제가 진행됩니다.</p>
              <p>2. PT 기본 시간은 1시간입니다.</p>
              <p>3. 쌍방 예약 확정 이후에는 일정 변경이 번거로울 수 있으니 예약정보를 반드시 다시 확인한 후 예약을 확정해 주세요.</p>
            </Typography>
          </RegisterWrapper>

          
          <RegisterWrapper>
            <RegisterButton href="pt_reservation" variant="contained">
              <Typography variant="h6">예약 및 결제하기</Typography>
            </RegisterButton> 
          </RegisterWrapper>

        </ReservationWrapper>
      </ContentsWrapper>
    </Wrapper>
  );
}

export default PtReservation;