import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider } from "react-router-dom";
import Router from "./Router";
import { Provider } from "react-redux";

import { ThemeProvider } from "styled-components";
import store from "./store";

const color = {
  primary: "#285943",
  secondary: "#D7FFF1",
  light: "#fffdfb",
  dark: "#24272b",
};

const windowSize = {
  small: 'screen and (max-width: "600px")',
  base: 'screen and (max-width: "768px")',
  large: 'screen and (max-width: "1024px")',
};

const fontSize = {
  xs: "0.5rem",
  sm: "0.75rem",
  base: "1rem",
  md: "1.25rem",
  lg: "1.5rem",
};

const theme = {
  color,
  windowSize,
  fontSize,
};

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <ThemeProvider theme={theme}>
    <Provider store={store}>
      <React.StrictMode>
        <RouterProvider router={Router} />
      </React.StrictMode>
    </Provider>
  </ThemeProvider>
);
