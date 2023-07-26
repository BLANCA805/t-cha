import { Link, Outlet } from "react-router-dom";

function TrainerDetail() {
  return (
    <div>
      <h3>트레이너 상세 페이지 입니다.</h3>
      <Link to="info">트레이너 정보</Link>
      <br />
      <Link to="review">트레이너 리뷰</Link>
      <Outlet />
    </div>
  );
}

export default TrainerDetail;
