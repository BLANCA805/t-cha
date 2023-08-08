import React from "react";

import { InquiryData } from "src/interface";

const Inquiry: React.FC<InquiryData> = ({ data }) => {
  console.log(data);
  return (
    <div>
      Q&A 입니다.
      {data.map((inquiry: typeof data[0]) =>(
        <div key = {inquiry.questionId}>
          <h3>{inquiry.questionId}.제목: {inquiry.title}</h3>
          <p>내용: {inquiry.content}</p>
          <p>작성자: {inquiry.userProfileId}</p>
        </div>
      ))}
    </div>
  );
};

export default Inquiry;
