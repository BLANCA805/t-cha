import { Outlet } from "react-router-dom";
import styled from "styled-components";

import SideBar from "@shared/side-bar";
import Test from "./test";
import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
  html, body {
    /* margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
    width: 100vw;
    height: 100vh; */
    background-color:#D8E6E7;
  }
`;

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  min-height: 100vh;
  min-width: 100vh;
  background-color: ${({ theme }) => theme.color.secondary};
  color: ${({ theme }) => theme.color.primary};
`;

// const Wrapper = styled.div`
//   position: fixed;
//   top: 0;
//   left: 0;
//   width: 100%;
//   height: 100vh;
//   background-color: ${({ theme }) => theme.color.secondary};
//   color: ${({ theme }) => theme.color.primary};
//   overflow: auto;
// `;

const Container = styled.div`
  width: 100%;
`;

function App() {
  return (
    <>
    <GlobalStyle />
      <Wrapper>
        <Container>
          <Outlet />
          <SideBar />
        </Container>
      </Wrapper>
  </>
  );
}

export default App;
