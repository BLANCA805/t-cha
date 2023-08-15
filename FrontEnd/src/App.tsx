import { Outlet } from "react-router-dom";
import styled from "styled-components";

import SideBar from "@shared/side-bar";
import Test from "./test";

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  min-height: 100vh;
  min-width: 100vh;
  background-color: ${({ theme }) => theme.color.secondary};
  color: ${({ theme }) => theme.color.primary};
`;

const Container = styled.div`
  width: 100%;
`;

function App() {
  return (
    <Wrapper> 
      <Container>
        <Outlet />
        <SideBar />
      </Container>
    </Wrapper>
  );
}

export default App;
