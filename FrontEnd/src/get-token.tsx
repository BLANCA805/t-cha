import { useSearchParams } from "react-router-dom";

function GetToken() {
  const [authParams, setAuthParams] = useSearchParams();
  const access = authParams.get("access_token");
  const refresh = authParams.get("refresh_token");
  const email = authParams.get("email");
  return (
    <div>
      <p>{access}</p>
      <p>{refresh}</p>
      <p>{email}</p>
    </div>
  );
}

export default GetToken;
