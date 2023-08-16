import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import { RootState } from "src/redux/store";

interface CheckLoginedProps {
  component: React.ReactElement<any>;
}

const CheckLogined = ({ component: Component }: CheckLoginedProps) => {
  const token = useSelector((state: RootState) => state.auth.accessToken);
  if (!token) {
    alert("로그인이 필요한 페이지입니다.");
    return <Navigate to="/login" />;
  }
  return Component;
};

export default CheckLogined;
