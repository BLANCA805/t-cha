import React from "react";
import ReactDOM from "react-dom/client";
import Router from "./Router";
import { RouterProvider } from "react-router-dom";
import store from "./redux/store";
import { Provider } from "react-redux";

import { PersistGate } from "redux-persist/integration/react";
import { persistStore } from "redux-persist";

import { ThemeProvider } from "styled-components";
import {
  createTheme,
  ThemeProvider as MuiThemeProvider,
} from "@mui/material/styles";
import Asset4 from "./shared/icons/Asset4.png";

const color = {
  primary: "#6E7783",
  secondary: "#D8E6E7",
  light: "#fffdfb",
  dark: "#24272b",
  tcha:"#125B51",
  tchamid:"#0a8d85",
  tchalight:"#1BA693",

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

// MUI 테마 생성
const muiTheme = createTheme({
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        "*": {
          "&:hover": {
            cursor: `url(${Asset4}), pointer`,
          },
        },
      },
    },
  },
});

export let persistor = persistStore(store);

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <MuiThemeProvider theme={muiTheme}>
    <ThemeProvider theme={theme}>
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>
          {/* <React.StrictMode> */}
          <RouterProvider router={Router} />
          {/* </React.StrictMode> */}
        </PersistGate>
      </Provider>
    </ThemeProvider>
  </MuiThemeProvider>
);
