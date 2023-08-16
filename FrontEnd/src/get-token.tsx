import { useDispatch } from "react-redux";
import { Navigate, useSearchParams } from "react-router-dom";
import { AppDispatch } from "./redux/store";
import { getUserData, logIn } from "./redux/slicers";
import axios from "axios";
import { api } from "@shared/common-data";

function GetToken() {
  const [authParams, setAuthParams] = useSearchParams();
  const access = authParams.get("access_token");
  const refresh = authParams.get("refresh_token");
  const email = authParams.get("email");

  const dispatch = useDispatch<AppDispatch>();

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
    })
    .catch((error) => {
      console.log(error);
    });

  return <Navigate to="/" />;
}

export default GetToken;
