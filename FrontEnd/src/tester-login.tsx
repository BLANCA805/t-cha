import { useDispatch } from "react-redux";
import { AppDispatch } from "./redux/store";
import { getUserData, logIn } from "./redux/slicers";
import axios from "axios";
import { api } from "@shared/common-data";
import { Navigate } from "react-router-dom";

const TesterLogin = () => {
  const dispatch = useDispatch<AppDispatch>();
  const access = "tester";
  const refresh = "tester";
  const email = "tcha_tester@gmail.com";

  dispatch(
    logIn({
      accessToken: access,
      refreshToken: refresh,
      email: email,
    })
  );

  axios
    .get(`${api}/users?email=${email}`)
    .then((response) => {
      dispatch(getUserData(response.data));
      console.log("테스터로 성공적으로 로그인 했습니다!");
    })
    .catch((error) => {
      console.log(error);
    });

  return <Navigate to="/" />;
};

export default TesterLogin;
