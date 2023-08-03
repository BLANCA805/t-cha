import styled from "styled-components";

export interface detailData {
  data: {
    id: string;
    introduction: string;
    tags: string;
    title: string;
    content: string;
    images: Array<string>;
    profileImg: string;
    profileName: string;
  };
}

const Wrapper = styled.div`
  margin: 1%;
  padding: 3%;
`;

function TrainerDetail() {
  console.log();
  return <Wrapper>트레이너 상세 정보 페이지 입니다.</Wrapper>;
}

export default TrainerDetail;
