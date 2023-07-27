import { useEffect, useState } from "react";
import axios from "axios";

import useAxios from "src/hooks/use-axios";
import TrainerListItem from "@trainer-list/trainer-list-item";

import TrainerListHeader from "@trainer-detail/trainer-list-header";

import styled from "styled-components";

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: white;
`;

function TrainerList() {
  const [items, setItems] = useState<Array<any>>([]);

  useAxios({
    method: "get",
    url: "https://picsum.photos/v2/list?page=12&limit=5",
  });

  useEffect(() => {
    axios
      .get("http://70.12.245.39:8080/trainers")
      .then((response) => {
        setItems(response.data);
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  return (
    <Wrapper>
      <TrainerListHeader />
      {items.map((item) => (
        <TrainerListItem data={item} />
      ))}
    </Wrapper>
  );
}

export default TrainerList;
