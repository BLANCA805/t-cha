import { Outlet, useNavigation } from "react-router-dom";
import styled from "styled-components";

import DesktopSideBar from "@shared/desktop-side-bar";
import { useMediaQuery } from "react-responsive";
import { createGlobalStyle } from "styled-components";
import MyFont1 from "./assets/fonts/jamsilOtfThin1.otf";
import MyFont2 from "./assets/fonts/jamsilOtfLight2.otf";
import MyFont3 from "./assets/fonts/jamsilOtfRegular3.otf";
import MyFont4 from "./assets/fonts/jamsilOtfMedium4.otf";
import MyFont5 from "./assets/fonts/jamsilOtfBold5.otf";
import MyFont6 from "./assets/fonts/jamsilOtfExtraBold6.otf";
import Asset3 from "./shared/icons/Asset3.png";
import Asset4 from "./shared/icons/Asset4.png";
import Asset5 from "./shared/icons/Asset5.png";
import MobileBottomBar from "@shared/mobile-bottom-bar";
import { fontFamily } from "@mui/system";

const GlobalStyle = createGlobalStyle`
  .box {
    box-sizing: border-box;
  }
  @font-face {
    font-family: 'jamsil1'; 
    src: url(${MyFont1}) format('opentype'); 
  }
  @font-face {
    font-family: 'jamsil2'; 
    src: url(${MyFont2}) format('opentype'); 
  }
  @font-face {
    font-family: 'jamsil3'; 
    src: url(${MyFont3}) format('opentype'); 
  }
  @font-face {
    font-family: 'jamsil4'; 
    src: url(${MyFont4}) format('opentype'); 
  }
  @font-face {
    font-family: 'jamsil5'; 
    src: url(${MyFont5}) format('opentype'); 
  }
  @font-face {
    font-family: 'jamsil6'; 
    src: url(${MyFont6}) format('opentype'); 
  }

  
  body {
    
    cursor: url(${Asset5}), auto ;
    /* cursor: url(${Asset4}), pointer; */

    font-family: "jamsil1";
    font-size:1rem;
    @media (max-width: 767px) {
      font-size:0.5rem;
    }
  }
  
  [style*="cursor:pointer;"],
  a, button, input[type="button"], input[type="submit"] {
  cursor: url(${Asset3}), pointer !important;
  }
  
  h1{
    font-family:"jamsil6";
    font-size:2rem;
    @media (max-width: 767px) {
      font-size:1.5rem;
    }
  }
  h2{
    font-family:"jamsil5";
    font-size:1.75rem;
    @media (max-width: 767px) {
      font-size:1.35rem;
    }
  }
  h3{
    font-family:"jamsil4";
    font-size:1.5rem;
    @media (max-width: 767px) {
      font-size:1.25rem;
    }
  }
  h4{
    font-family:"jamsil3";
    font-size:1.35rem;
    @media (max-width: 767px) {
      font-size:1.2rem;
    }
  }
  h5{
    font-family:"jamsil2";
    font-size:1.35rem;
    @media (max-width: 767px) {
      font-size:1.2rem;
    }
  }
  h6{
    font-family:"jamsil1";
    font-size:1.25rem;
    @media (max-width: 767px) {
      font-size:1rem;
    }
  }
`;

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  /* min-width: 1660px; */
  height: 100%;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.color.secondary};
  /* background-color: #eff5f4; //이거 괜찮음  */
  /* background: radial-gradient( #11a39c, #2a4d43); */

  /* background-color: #ecf0ef; */
  /* background-color: #eeeeee; */
  /* background-color: #e0e0e0; */
  /* background-color: #3f3f3f; */
  /* background-color: #5e5e5e; */
  @media (max-width: 767px) {
    min-width: 0px;
  }
  color: ${({ theme }) => theme.color.primary};
  /* color: ${({ theme }) => theme.color.dark}; */
`;

const Container = styled.div`
  flex: 1;
  display: flex;
  flex-shrink: 1;
  justify-content: center;
  z-index: 0;
  width: 70vw;
  @media (max-width: 767px) {
    padding-bottom: 6.5rem;
    overflow-y: auto;
  }
`;

type MediaQueryProps = {
  children: React.ReactNode;
};

const Desktop: React.FC<MediaQueryProps> = ({ children }) => {
  const isDesktop = useMediaQuery({ minWidth: 768 });
  return isDesktop ? <>{children}</> : null;
};

const Mobile: React.FC<MediaQueryProps> = ({ children }) => {
  const isMobile = useMediaQuery({ maxWidth: 767 });
  return isMobile ? <>{children}</> : null;
};

function App() {
  //모바일모드 시 브라우저 상하단 메뉴 때문에 실제로는 개발자도구보다 뷰포트 높이가 깎여서 소실됨
  //이러한 현상을 방지하는 코드
  //이러면 app.js가 렌더링될 때 이 함수가 사용자의 뷰포트 높이를 계산해준다함
  //모바일 100vh 필요한 곳에서 height: calc(var(--vh, 1vh) * 100); 사용하면 된다고 함
  // function setScreenSize() {
  //   let vh = window.innerHeight * 0.01;
  //   document.documentElement.style.setProperty("--vh", `${vh}px`);
  // }
  // useEffect(() => {
  //   setScreenSize();
  // });

  // 모바일 웹에서 올바른 영역을 잡기 위한 작업
  let vh = window.innerHeight * 0.01;
  document.documentElement.style.setProperty("--vh", `${vh}px`);

  // 화면 크기 변경 감지
  window.addEventListener("resize", () => {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  });
  const navigation = useNavigation();
  return (
    <>
      <GlobalStyle />
      <div className={navigation.state === "loading" ? "loading" : ""}>
        <Wrapper>
          <Desktop>
            <DesktopSideBar />
          </Desktop>

          <Mobile>
            <MobileBottomBar />
          </Mobile>

          <Container>
            <Outlet />
          </Container>
        </Wrapper>
      </div>
    </>
  );
}

export default App;
