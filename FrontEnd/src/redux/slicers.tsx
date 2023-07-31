import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isLogined: false,
  token: "",
  userName: "",
  profileImage: "",
};

const authSlice = createSlice({
  name: "auth",
  initialState: initialState,
  reducers: {
    logIn: (state, action) => {
      state.isLogined = true;
      state.token = action.payload.token;
      state.userName = action.payload.userName;
      state.profileImage = action.payload.profileImage;
    },
    logOut: (state) => {
      state.isLogined = false;
      state.token = "";
      state.userName = "";
      state.profileImage = "";
    },
  },
});

export const { logIn, logOut } = authSlice.actions;

export default authSlice.reducer;
