import styled from "styled-components";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.color.secondary};
  display: flex;
  justify-content: center;
  align-items: center;
`;

const OtherVideo = styled.div`
  flex: 7;
  height: 95vh;
  margin: 1rem;
  background-color: ${({ theme }) => theme.color.light};
`;

const LiveToolBox = styled.div`
  flex: 3;
  height: 95vh;
  margin: 1rem;
  background-color: ${({ theme }) => theme.color.light};
`;

export default function Test() {
  return (
    <Wrapper>
      <OtherVideo></OtherVideo>
      <LiveToolBox></LiveToolBox>
    </Wrapper>
  );
}
