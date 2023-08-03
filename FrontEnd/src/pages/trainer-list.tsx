import { useEffect, useState } from "react";
import axios from "axios";

import { api } from "@shared/common-data";

import TrainerListHeader from "@trainer-list/trainer-list-header";
import TrainerListItem from "@trainer-list/trainer-list-item";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: white;
`;

function TrainerList() {
  const [items, setItems] = useState([]);

  useEffect(() => {
    axios
      .get(`${api}/trainers`)
      .then((response) => {
        setItems(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <Wrapper>
      <TrainerListHeader />
      {items.map((item, index) => (
        <TrainerListItem data={item} key={index} />
      ))}
    </Wrapper>
  );
}

export default TrainerList;
