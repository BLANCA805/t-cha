import { createSlice, combineReducers } from "@reduxjs/toolkit";

const authInitialState = {
  token: "",
};

const profileInitialState = {
  name: "",
  profileId: 0,
  trainerId: "",
};

const authSlice = createSlice({
  name: "auth",
  initialState: authInitialState,
  reducers: {
    logIn: (state, action) => {
      state.token = action.payload.token;
    },
    logOut: (state) => {
      state.token = "";
    },
  },
});

const profileSlice = createSlice({
  name: "profile",
  initialState: profileInitialState,
  reducers: {
    registTrainer: (state, action) => {
      state.trainerId = action.payload.trainerId;
    },
    postProfile: (state, action) => {
      state.name = action.payload.name;
      state.profileId = action.payload.id;
    },
    deleteProfile: (state) => {
      state.name = "";
      state.profileId = 0;
      state.trainerId = "";
    },
    test: (state) => {},
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
