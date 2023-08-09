import React from "react";

import { NoticeData } from "src/interface";

const Notice: React.FC<NoticeData> = ({ data }) => {
  console.log(data);

  return (
    <div>
      ##공지사항 입니다.##
      {data.map((notice: typeof data[0]) =>(
        <div key = {notice.id}>
          <h3>{notice.id}.제목: {notice.title} [{notice.status}]</h3>
          <p>내용: {notice.content}</p>
        </div>
      ))}
    </div>
  );
};

export default Notice;
