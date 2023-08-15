import DateTimePicker from "@shared/date-time-picker";
import { useMediaQuery } from "react-responsive";

import { TextField } from "@mui/material";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Button from "@mui/material/Button";
import { SmDateTimePicker } from "@shared/date-time-picker";
import styled from "styled-components";
import { TchaButton, ReverseTchaButton } from "@shared/button";
import { SearchFormData, TrainerListHeaderProps } from "src/interface";
import { useEffect, useState } from "react";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin: 1% 0%;
  padding: 1%;
  border-radius: 5px;
  background-color: white;
`;

const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

const TextFieldWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 1%;
  width: 100%;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 1% 0%;
`;

const AccordionContainer = styled.div`
  display: flex;
  width: 100%;
  margin: 1%;
`;
const StyledAccordion = styled(Accordion)`
  width: 80%;
  @media (max-width: 767px) {
    max-width: 20rem !important;
  }
`;
const StyledText = styled.h6`
  margin: 0.75% 1%;
  @media (max-width: 767px) {
    /* font-size:0.5rem; */
  }
`;

function TrainerListHeader({
  searchTrainer,
  sortTrainer,
}: TrainerListHeaderProps) {
  const [searchKeyword, setSearchkeyword] = useState("");

  const handleSearchKeyword = (event: any) => {
    setSearchkeyword(event.target.value);
  };

  const [sortCondition, setSortCondition] = useState("");

  const handleSortCondition = (condition: string) => {
    setSortCondition(condition);
  };

  useEffect(() => {
    sortTrainer(sortCondition);
  }, [sortCondition]);

  const searchForm = {
    keyword: searchKeyword,
  };

  const isDesktop = useMediaQuery({ minWidth: 768 });
  const isMobile = useMediaQuery({ maxWidth: 767 });

  const sortButtons = [
    { value: "", text: "최근 등록 순" },
    { value: "/sorted-by-star", text: "별점 높은 순" },
    { value: "/sorted-by-pt", text: "PT 누적 횟수 순" },
    { value: "/sorted-by-bookmark", text: "즐겨찾기 많은 순" },
    { value: "/sorted-by-review", text: "리뷰 많은 순" },
  ];

  return (
    <Wrapper>
      <SearchContainer>
        <TextFieldWrapper>
          <TextField
            id="outlined-basic"
            label="트레이너 이름 또는 키워드를 검색하세요"
            variant="outlined"
            style={{ width: "100%" }}
            value={searchKeyword}
            onChange={handleSearchKeyword}
          />
        </TextFieldWrapper>
        <TchaButton
          onClick={() => searchTrainer(searchForm)}
          style={{
            maxWidth: "8rem",
            width: "30%",
            height: "3.5rem",
            color: "white",
          }}
        >
          <StyledText>검색하기</StyledText>
        </TchaButton>
      </SearchContainer>
      <ButtonContainer>
        {sortButtons.map((button) => {
          const ButtonComponent =
            sortCondition === button.value ? TchaButton : ReverseTchaButton;

          return (
            <ButtonComponent
              key={button.value}
              onClick={() => handleSortCondition(button.value)}
              style={{ width: "20%", color: "white" }}
            >
              <StyledText>{button.text}</StyledText>
            </ButtonComponent>
          );
        })}
      </ButtonContainer>
      <AccordionContainer>
        <StyledAccordion>
          <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1a-content"
            id="panel1a-header"
          >
            <StyledText>날짜 및 시간 선택</StyledText>
          </AccordionSummary>
          <AccordionDetails>
            {isDesktop && <DateTimePicker />}
            {isMobile && <SmDateTimePicker />}
          </AccordionDetails>
        </StyledAccordion>
      </AccordionContainer>
    </Wrapper>
  );
}

export default TrainerListHeader;
