import { createBrowserRouter } from "react-router-dom";

import App from "./App";

import Test from "./test";

import Main from "@/main";

import ChatList from "@/chat-list";
import ChatRoom from "@/chat-room";

import CustomerCenter from "@/customer-center";
import Inquiry from "@customer-center/inquiry";
import Notice from "@customer-center/notice";

import User from "@/user";
import BookMarkedTrainerList from "@/bookmarked-trainer-list";
import UserSchedule from "@/user-schedule";
import UserInfoModify from "@/user-info-modify";
import PaymentDetail from "@/payment-detail";
import ReviewWrittenByUser from "@/review-written-by-user";
import TrainerRegistration from "@/trainer-registration";
import ExerciseLog from "@/exercise-log";

import TrainerList from "@/trainer-list";
import TrainerInfo from "@/trainer-info";
import TrainerDetail from "@trainer-info/trainer-detail";
import TrainerReview from "@trainer-info/trainer-review";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "",
        element: <Main />,
      },
      {
        path: "customer_center",
        element: <CustomerCenter />,
        children: [
          {
            path: "notice",
            element: <Notice />,
          },
          {
            path: "inquiry",
            element: <Inquiry />,
          },
        ],
      },
      {
        path: "profile",
        element: <User />,
      },
      {
        path: "profile/bookmarked_trainers",
        element: <BookMarkedTrainerList />,
      },
      {
        path: "profile/schedule",
        element: <UserSchedule />,
      },
      {
        path: "profile/info_modify",
        element: <UserInfoModify />,
      },
      {
        path: "profile/payment_detail",
        element: <PaymentDetail />,
      },
      {
        path: "profile/review",
        element: <ReviewWrittenByUser />,
      },
      {
        path: "profile/chat",
        element: <ChatList />,
        children: [
          {
            path: "chatid",
            element: <ChatRoom />,
          },
        ],
      },
      {
        path: "profile/trainer_registration",
        element: <TrainerRegistration />,
      },
      {
        path: "profile/exercise_log",
        element: <ExerciseLog />,
      },
      {
        path: "trainer",
        element: <TrainerList />,
      },
      {
        path: "trainer/info",
        element: <TrainerInfo />,
        children: [
          {
            path: "detail",
            element: <TrainerDetail />,
          },
          {
            path: "review",
            element: <TrainerReview />,
          },
        ],
      },
    ],
  },
  {
    path: "test",
    element: <Test />,
  },
]);

export default router;
