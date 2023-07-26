import { createBrowserRouter } from "react-router-dom";

import App from "./App";

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

import TrainerList from "@/trainer-list";
import TrainerDetail from "@/trainer-detail";
import TrainerInfo from "@trainer-detail/trainer-info";
import TrainerReview from "@trainer-detail/trainer-review";

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
        path: "customer-center",
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
        path: ":user-id",
        element: <User />,
      },
      {
        path: ":user-id/bookmarked-trainers",
        element: <BookMarkedTrainerList />,
      },
      {
        path: ":user-id/schedule",
        element: <UserSchedule />,
      },
      {
        path: ":user-id/info-modify",
        element: <UserInfoModify />,
      },
      {
        path: ":user-id/payment-detail",
        element: <PaymentDetail />,
      },
      {
        path: ":user-id/review",
        element: <ReviewWrittenByUser />,
      },
      {
        path: ":user-id/chat",
        element: <ChatList />,
        children: [
          {
            path: "chatid",
            element: <ChatRoom />,
          },
        ],
      },
      {
        path: "trainer",
        element: <TrainerList />,
      },
      {
        path: "trainer/:trainer-id",
        element: <TrainerDetail />,
        children: [
          {
            path: "info",
            element: <TrainerInfo />,
          },
          {
            path: "review",
            element: <TrainerReview />,
          },
        ],
      },
    ],
  },
]);

export default router;
