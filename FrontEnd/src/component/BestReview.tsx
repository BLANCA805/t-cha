import styled from "styled-components";

const Container = styled.div`
  background-color: white;
  padding: 2%;
  border-radius: 10px;
  margin-top: 3%;
  margin-bottom: 3%;
`;

const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;

function BestReview() {
  const bestReviewItem = [
    {
      context: "정말 좋아요1",
      star: 5,
    },
    {
      context: "정말 좋아요2",
      star: 4,
    },
    {
      context: "정말 좋아요3",
      star: 5,
    },
    {
      context: "정말 좋아요4",
      star: 4.5,
    },
    {
      context: "정말 좋아요5",
      star: 3.5,
    },
  ];
  return (
    <Container>
      <h4>서비스 후기</h4>
      <div>
        {bestReviewItem.map((item, index) => (
          <Wrapper key={index}>
            <h5>{item.context}</h5>
            <h5>{item.star}</h5>
          </Wrapper>
        ))}
      </div>
    </Container>
  );
}

export default BestReview;
