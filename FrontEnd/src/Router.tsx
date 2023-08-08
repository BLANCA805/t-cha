import { createBrowserRouter } from "react-router-dom";

import App from "./App";

import Test from "./test";

import Main from "@/main";

import ChatList from "@/chat-list";
import ChatRoom from "@/chat-room";

import CustomerCenter from "@/customer-center";
import WriteInquiry from "@customer-center/write-inquiry";
import WriteNotice from "@customer-center/write-notice";

import User from "@/user";
import BookmarkedTrainerList from "@/bookmarked-trainer-list";
import UserSchedule from "@/user-schedule";
import UserInfoModify from "@/user-info-modify";
import PaymentDetail from "@/payment-detail";
import ReviewWrittenByUser from "@/review-written-by-user";
import TrainerRegistration from "@/trainer-registration";
import ExerciseLog from "@/exercise-log";

import TrainerList from "@/trainer-list";
import TrainerInfo from "@/trainer-info";
import TrainerInfoModify from "@user-trainer/trainer-info-modify";
import TrainerSchedule from "@user-trainer/trainer-schedule";
import CreateClasses from "@user-trainer/create-classes";
import PtudentList from "@user-trainer/ptudent-list";
import PtReservation from "@/pt-reservation";
import PtRoom from "@/pt-room";

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
        path: "pt/:class_id",
        element: <PtRoom />,
      },
      {
        path: "customer_center",
        element: <CustomerCenter />,
      },
      {
        path: "customer_center/write_inquiry",
        element: <WriteInquiry />,
      },
      {
        path: "customer_center/write_notice",
        element: <WriteNotice />,
      },
      {
        path: "profile",
        element: <User />,
      },
      {
        path: "profile/trainer_info_modify",
        element: <TrainerInfoModify />,
      },
      {
        path: "profile/trainer_schedule",
        element: <TrainerSchedule />,
      },
      {
        path: "profile/trainer_schedule/create_classes",
        element: <CreateClasses />,
      },
      {
        path: "profile/trainer_ptudent_list",
        element: <PtudentList />,
      },
      {
        path: "profile/bookmarked_trainers",
        element: <BookmarkedTrainerList />,
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
      },
      {
        path: "trainer/pt_reservation",
        element: <PtReservation />,
      },
    ],
  },
  {
    path: "test",
    element: <Test />,
  },
]);

export default router;
