import React from "react";

import { InquiryData } from "src/interface";

const Inquiry: React.FC<InquiryData> = ({ data }) => {
  console.log(data);
  return <div>Q&A 입니다.</div>;
};

export default Inquiry;
