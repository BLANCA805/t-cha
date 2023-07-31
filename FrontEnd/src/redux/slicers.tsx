import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isLogined: false,
  token: "",
};

const authSlice = createSlice({
  name: "auth",
  initialState: initialState,
  reducers: {
    logIn: (state, action) => {
      state.isLogined = true;
      state.token = action.payload.token;
    },
    logOut: (state) => {
      state.isLogined = false;
      state.token = "";
    },
  },
});

export const { logIn, logOut } = authSlice.actions;

export default authSlice.reducer;
