import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import { NoticeData, InquiryData } from "src/interface";
import { api } from "@shared/common-data";

import Notice from "@customer-center/notice";
import Inquiry from "@customer-center/inquiry";

import ToggleButtons from "@shared/toggle-button";
import { TitleWrapper, PageTitleText } from "@shared/page-title";
import { TchaButton } from "@shared/button";
import styled from "styled-components";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width:97%;
  margin-top:1%;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 1%;
  padding: 3%;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.light};
`;
const ButtonWrapper = styled.div`
  display:flex;
  width:100%;
  justify-content: center;
`

function CustomerCenter() {
  const [tab, setTab] = useState<string>("notice");
  const [notice, getNotice] = useState<NoticeData>();
  const [inquiry, getInguiry] = useState<InquiryData>();

  const clickTab = (name: string) => {
    setTab(name); // 새로운 탭 클릭 시, 상태 변경
  };

  useEffect(() => {
    axios
      .all([
        axios.get(`${api}/notices`),
        axios.get(`${api}/questions?page=1&size=5`),
      ])
      .then(
        axios.spread((notice, inquiry) => {
          getNotice(notice.data);
          getInguiry(inquiry.data);
        })
      )
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <Container>
      <TitleWrapper>
        <PageTitleText>고객센터</PageTitleText>
        <ButtonWrapper>
          {/* <TchaButton>
            <h5> 공지사항 작성</h5>
          </TchaButton> */}
          <Link to={"write_notice"}>공지사항 작성</Link>
          <Link to={"write_inquiry"}>문의사항 작성</Link>
        </ButtonWrapper>
      </TitleWrapper>
      <Wrapper>
        <ToggleButtons
          tabs={[
            { text: "공지사항", name: "notice" },
            { text: "Q&A", name: "inquiry" },
          ]}
          width="100%"
          clickTab={clickTab}
        />
        {tab === "notice" && notice && (
          <Notice data={notice.data} pageInfo={notice.pageInfo} />
        )}
        {tab === "inquiry" && inquiry && (
          <Inquiry data={inquiry.data} pageInfo={inquiry.pageInfo} />
        )}
      </Wrapper>
    </Container>
  );
}

export default CustomerCenter;
