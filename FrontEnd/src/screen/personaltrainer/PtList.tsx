import { Link } from "react-router-dom";

function PtList() {
  return (
    <div>
      <h3>트레이너 리스트 입니다.</h3>
      <Link to="trainerid">트레이너 상세보기</Link>
    </div>
  );
}

export default PtList;
