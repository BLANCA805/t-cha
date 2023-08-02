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
    id: string;
    profileName: string;
    profileImg: string;
    introduction: string;
    tags: string;
    stars: number;
    createdAt: string;
    userCount: number;
    ptCount: number;
    reviewCount: number;
    revisitGrade: number;
  };
}

function TrainerListItem(props: PtListItemProps) {
  const moveToDetail = useNavigate();

  return (
    <Wrapper onClick={() => moveToDetail(`info/detail`)}>
      <Avatar
        style={{ width: "10%", height: "10%", margin: "5%" }}
        alt=""
        src=""
      /> 
      <DataWrapper>
        <h2>{props.data.profileName}</h2>
        <KeywordWrapper>
          {props.data.tags.split(",").map((tag, index) => (
            <div style={{ marginLeft: "5px", marginRight: "5px" }} key={index}>
              <h4>#{tag}</h4>
            </div>
          ))}
        </KeywordWrapper>
        <h4>등록 일자 : {props.data.createdAt}</h4>
        <Container>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            회원 수 : {props.data.userCount}
          </h5>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            예약 수 : {props.data.ptCount}
          </h5>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            리뷰 수 : {props.data.reviewCount}
          </h5>
          <h5 style={{ marginLeft: "5px", marginRight: "5px" }}>
            재등록율 : {props.data.revisitGrade} %
          </h5>
        </Container>
      </DataWrapper>
    </Wrapper>
  );
}

export default TrainerListItem;
