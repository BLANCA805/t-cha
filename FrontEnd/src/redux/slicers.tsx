import { createSlice, combineReducers } from "@reduxjs/toolkit";

const authInitialState = {
  accessToken: "",
  refreshToken: "",
  email: "",
};

const profileInitialState = {
  name: "",
  id: "",
  profileId: 0,
  profileImage: "",
  trainerId: "",
};

const ptLiveInitialState = {
  ov: null, // openvidu 객체
  userOpenViduToken: "",
  sessionId: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState: authInitialState,
  reducers: {
    logIn: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.refreshToken = action.payload.refreshToken;
      state.email = action.payload.email;
    },
    logOut: (state) => {
      state.accessToken = "";
      state.refreshToken = "";
      state.email = "";
    },
  },
});

const profileSlice = createSlice({
  name: "profile",
  initialState: profileInitialState,
  reducers: {
    getUserData: (state, action) => {
      state.name = action.payload.name;
      state.id = action.payload.userId;
      state.profileId = action.payload.userProfileId;
      state.profileImage = action.payload.userProfileImage;
      state.trainerId = action.payload.trainerId;
    },
    registTrainer: (state, action) => {
      state.trainerId = action.payload.trainerId;
    },
    deleteProfile: (state) => {
      state.name = "";
      state.id = "";
      state.profileId = 0;
      state.trainerId = "";
      state.profileImage = "";
    },
    modifyProfile: (state, action) => {
      if (action.payload.profileImage) {
        state.name = action.payload.name;
        state.profileImage = action.payload.profileImage;
      } else {
        state.name = action.payload.name;
      }
    },
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
    },
    clearItems: (state) => {
      state.ov = null;
      state.sessionId = null;
      state.userOpenViduToken = "";
    },
  },
});

const rootReducer = combineReducers({
  auth: authSlice.reducer,
  profile: profileSlice.reducer,
  ptLive: ptLiveSlice.reducer,
});

export const { logIn, logOut } = authSlice.actions;
export const { getUserData, registTrainer, deleteProfile, modifyProfile } =
  profileSlice.actions;
export const { setOV, setOpenViduToken, setSessionId, clearItems } =
  ptLiveSlice.actions;

export default rootReducer;
