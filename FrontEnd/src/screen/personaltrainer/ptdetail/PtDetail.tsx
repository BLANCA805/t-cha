import { Link, Outlet } from "react-router-dom";

function PtDetail() {
  return (
    <div>
      <h3>트레이너 상세 페이지 입니다.</h3>
      <Link to="trainer-info">트레이너 정보</Link>
      <br />
      <Link to="trainer-review">트레이너 리뷰</Link>
      <Outlet />
    </div>
  );
}

export default PtDetail;
