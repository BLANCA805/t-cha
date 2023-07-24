import styled from "styled-components";

import { Link } from "react-router-dom";

const Wrapper = styled.div`
  margin: 0.5%;
  padding: 3rem;
  background-color: white;
  width: 20%;
  border-radius: 10px;
`;

function SideBar() {
  return (
    <Wrapper>
      <h3>사이드바도 어디에서든 보여야합니다.</h3>
      <ul>
        <li>
          <Link to="auth">로그인</Link>
        </li>
        <li>
          <Link to="">홈으로</Link>
        </li>
        <li>
          <Link to="userid/calendar">내 캘린더</Link>
        </li>
        <li>
          <Link to="chat">채팅 목록 보기</Link>
        </li>
        <li>
          <Link to="pt">트레이너들 보러가기</Link>
        </li>
        <li>
          <Link to="customer-center">고객센터</Link>
        </li>
      </ul>
    </Wrapper>
  );
}

export default SideBar;
