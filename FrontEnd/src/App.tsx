import React from "react";
import { Outlet } from "react-router-dom";
import  styled  from "styled-components";

import SideBar from "./SideBar";

const Wrapper = styled.div`
  display: flex;
  height: 100vh;
  background-color: #e2f2ef;
`;

const Container = styled.div`
  width: 100%;
`;

function App() {
  return (
    <Wrapper>
      <SideBar />
      <Container>
        <h1>App 전체에 다 보여야합니다.</h1>
        <Outlet />
      </Container>
    </Wrapper>
  );
}

export default App;
