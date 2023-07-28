import { useState } from "react";
import axios, { AxiosRequestConfig } from "axios";

type AxiosProps = {
  method: "get" | "post" | "put" | "delete";
  url: string;
  data?: any;
  config?: AxiosRequestConfig;
};

const useAxios = <T,>({
  method,
  url,
  config,
}: AxiosProps): [
  {
    response: T | undefined;
    error: string;
  },
  () => void
] => {
  const [response, setResponse] = useState<T>();
  const [error, setError] = useState("");

  const execution = () => {
    axios[method](url, config)
      .then((response) => {
        setResponse(response.data);
      })
      .catch((error) => {
        setError(error);
      });
  };

  return [{ response, error }, execution];
};

export default useAxios;
