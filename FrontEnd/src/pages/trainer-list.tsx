import { useEffect, useState } from "react";
import axios from "axios";

import TrainerListItem from "@trainer-list/trainer-list-item";

import TrainerListHeader from "src/containers/trainer-info/trainer-list-header";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: white;
`;

function TrainerList() {
  const [items, setItems] = useState<Array<any>>([]);

  useEffect(() => {
    axios
      .get("http://i9a805.p.ssafy.io:8080/trainers")
      .then((response) => {
        setItems(response.data);
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
