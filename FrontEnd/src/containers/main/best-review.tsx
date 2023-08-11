import styled from "styled-components";

const Container = styled.div`
  background-color: white;
  padding: 2%;
  border-radius: 10px;
  /* margin-top: 3%; */
  margin-bottom: 3%;
`;

const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

function BestReview() {
  const bestReviewItem = [
    {
      context: "전문 PT강사들을 어디서나 만날수 있어요",
      star: 5,
    },
    {
      context: "야근이 많은 직장인인데, 새벽에도 이용해서 좋아요",
      star: 4,
    },
    {
      context: "자세 교정부터 세세한 일지까지 다 보내주세요 ",
      star: 5,
    },
    {
      context: "정원쌤 덕분에 제 삶이 바뀌었어요",
      star: 5,
    },
    {
      context: "내 운동 기록과 내용을 볼 수 있어 좋아요",
      star: 4.5,
    },
  ];
  return (
    <Container>
      <h2 style={{margin:"3%"}}>서비스 후기</h2>
      <div >
        {bestReviewItem.map((item, index) => (
          <Wrapper key={index}>
            <h6 className="context" style={{margin:"3%", fontSize:"0.8rem"}}>{item.context}</h6>
            <h6 className="star" style={{margin:"3%"}}>{item.star}</h6>
          </Wrapper>
        ))}
      </div>
    </Container>
  );
}

export default BestReview;
