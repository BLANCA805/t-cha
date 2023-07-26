import { Link } from "react-router-dom";

import styled from "styled-components";

import Avatar from "@mui/material/Avatar";

const Wrapper = styled.div`
  background-color: white;
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  display: flex;
`;

function PtListItem() {
  return (
    <Link to="trainerid/trainer-info">
      <Wrapper>
        <Avatar style={{ height: "" }} alt="" src="" />
      </Wrapper>
    </Link>
  );
}

export default PtListItem;
