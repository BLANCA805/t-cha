import { createSlice, combineReducers } from "@reduxjs/toolkit";

const authInitialState = {
  isLogined: false,
  token: "",
};

const profileInitialState = {
  name: "",
  profileId: 0,
  isTrainer: false,
};

const authSlice = createSlice({
  name: "auth",
  initialState: authInitialState,
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

const profileSlice = createSlice({
  name: "profile",
  initialState: profileInitialState,
  reducers: {
    registTrainer: (state) => {
      state.isTrainer = true;
    },
    postProfile: (state, action) => {
      state.name = action.payload.name;
      state.profileId = action.payload.id;
    },
    deleteProfile: (state) => {
      state.name = "";
      state.profileId = 0;
      state.isTrainer = false;
    },
    test: (state) => {
      state.isTrainer = false;
    },
  },
});

const rootReducer = combineReducers({
  auth: authSlice.reducer,
  profile: profileSlice.reducer,
});

export const { logIn, logOut } = authSlice.actions;
export const { registTrainer, postProfile, deleteProfile, test } =
  profileSlice.actions;

export default rootReducer;
