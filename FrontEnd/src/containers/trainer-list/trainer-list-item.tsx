import { useNavigate } from "react-router-dom";

import styled from "styled-components";

import Avatar from "@mui/material/Avatar";

const Wrapper = styled.div`
  background-color: white;
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  display: flex;
  align-items: center;
`;

const DataWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

const KeywordWrapper = styled.div`
  display: flex;
`;

const Container = styled.div`
  display: flex;
`;

interface PtListItemProps {
  data: {
    id: number;
    name: string;
    keywordTags: string[];
    registrationDate: string;
    studentCount: number;
    reservationCount: number;
    reviewCount: number;
    reReservationRate: number;
  };
}

function TrainerListItem(props: PtListItemProps) {
  const moveToDetail = useNavigate();
  return (
    <Wrapper onClick={() => moveToDetail(`${props.data.id}/info`)}>
      <Avatar
        style={{ width: "10%", height: "10%", margin: "5%" }}
        alt=""
        src=""
      /> 
      <DataWrapper>
        <h2>{props.data.name}</h2>
        <KeywordWrapper>
          {props.data.keywordTags.map((keywordData, index) => (
            <div style={{ marginLeft: "5px", marginRight: "5px" }} key={index}>
              <h4>#{keywordData}</h4>
            </div>
          ))}
        </KeywordWrapper>
        <h4>등록 일자 : {props.data.registrationDate}</h4>
        <Container>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            회원 수 : {props.data.studentCount}
          </h5>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            예약 수 : {props.data.reservationCount}
          </h5>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            리뷰 수 : {props.data.reviewCount}
          </h5>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            재등록율 : {props.data.reReservationRate} %
          </h5>
        </Container>
      </DataWrapper>
    </Wrapper>
  );
}

export default TrainerListItem;
