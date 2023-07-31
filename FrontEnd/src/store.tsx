import { createAction, createReducer, createSlice } from "@reduxjs/toolkit";

import { configureStore } from "@reduxjs/toolkit";

const initialState = {
  isLogined: false,
};

const userLogIn: any = createAction("LOGIN");
const userLogOut: any = createAction("LOGOUT");

const reducer = createReducer(initialState, {
  [userLogIn]: (state: any) => {
    state.isLogined = true;
  },
  [userLogOut]: (state: any) => {
    state.isLogined = false;
  },
});

const store = configureStore({ reducer });

export default store;

export const actionCreators = {
  userLogIn,
  userLogOut,
};
