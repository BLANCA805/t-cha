import { createSlice, combineReducers } from "@reduxjs/toolkit";

const authInitialState = {
  token: "",
};

const profileInitialState = {
  name: "",
  profileId: 0,
  profileImage: "",
  trainerId: "",
};

const ptLiveInitialState = {
  ov: null, // openvidu 객체
  userOpenViduToken: null,
  sessionId: null,
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
      state.profileImage = "";
    },
    modifyProfile: (state, action) => {
      state.name = action.payload.name;
      state.profileImage = action.payload.profileImage;
    },
    test: (state) => {},
  },
});

const ptLiveSlice = createSlice({
  name: "ptLive",
  initialState: ptLiveInitialState,
  reducers: {
    setOV: (state, action) => {
      state.ov = action.payload.ov;
    },
    setOpenViduToken: (state, action) => {
      state.userOpenViduToken = action.payload;
    },
    setSessionId: (state, action) => {
      state.sessionId = action.payload;
    }
  },
});

const rootReducer = combineReducers({
  auth: authSlice.reducer,
  profile: profileSlice.reducer,
  ptLive: ptLiveSlice.reducer,
});

export const { logIn, logOut } = authSlice.actions;
export const {
  registTrainer,
  postProfile,
  deleteProfile,
  modifyProfile,
  test,
} = profileSlice.actions;
export const {
  setOV,
  setOpenViduToken,
  setSessionId,
} = ptLiveSlice.actions;

export default rootReducer;
