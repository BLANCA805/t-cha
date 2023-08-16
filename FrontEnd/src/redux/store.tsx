import { configureStore } from "@reduxjs/toolkit";
import storageSession from "redux-persist/lib/storage/session";
import { persistReducer } from "redux-persist";

import rootReducer from "./slicers";

const persistConfig = {
  key: "root",
  storage: storageSession,
  whitelist: ["auth", "profile", "ptLive"],
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }),
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
