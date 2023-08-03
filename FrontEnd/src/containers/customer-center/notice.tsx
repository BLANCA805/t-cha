import React from "react";

import { NoticeData } from "src/interface";

const Notice: React.FC<NoticeData> = ({ data }) => {
  console.log(data);
  return <div>공지사항 입니다.</div>;
};

export default Notice;
