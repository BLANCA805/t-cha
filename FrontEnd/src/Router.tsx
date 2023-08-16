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

import TrainerList from "@/trainer-list";
import TrainerInfo from "@/trainer-info";
import TrainerInfoModify from "@user-trainer/trainer-info-modify";
import TrainerSchedule from "@user-trainer/trainer-schedule";
import CreateClasses from "@user-trainer/create-classes";
import PtReservation from "@/pt-reservation";
import PtRoom from "@/pt-room";
import ErrorPage from "@shared/error-page";
import GetToken from "./get-token";

import CheckLogined from "@shared/check-logined";
import Login from "@/login";
import TesterLogin from "./tester-login";
import TesterLogin2 from "./tester-login2";

const router = createBrowserRouter([
  {
    path: "/",
    errorElement: <ErrorPage />,
    element: <App />,
    children: [
      {
        path: "",
        element: <Main />,
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
        element: <CheckLogined component={<User />} />,
      },
      {
        path: "profile/trainer_info_modify",
        element: <CheckLogined component={<TrainerInfoModify />} />,
      },
      {
        path: "profile/trainer_schedule",
        element: <CheckLogined component={<TrainerSchedule />} />,
      },
      {
        path: "profile/trainer_schedule/create_classes",
        element: <CheckLogined component={<CreateClasses />} />,
      },
      {
        path: "profile/bookmarked_trainers",
        element: <CheckLogined component={<BookmarkedTrainerList />} />,
      },
      {
        path: "profile/schedule",
        element: <CheckLogined component={<UserSchedule />} />,
      },
      {
        path: "profile/info_modify",
        element: <CheckLogined component={<UserInfoModify />} />,
      },
      {
        path: "profile/payment_detail",
        element: <CheckLogined component={<PaymentDetail />} />,
      },
      {
        path: "profile/review",
        element: <CheckLogined component={<ReviewWrittenByUser />} />,
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
        element: <CheckLogined component={<TrainerRegistration />} />,
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
        element: <CheckLogined component={<PtReservation />} />,
      },
    ],
  },
  {
    path: "login",
    element: <Login />,
  },
  {
    path: "test",
    element: <Test />,
  },
  {
    path: "/get_token",
    element: <GetToken />,
  },
  {
    path: "pt",
    element: <PtRoom />,
  },
  {
    path: "변정원은귀엽습니다",
    element: <TesterLogin />,
  },
  {
    path: "변정원은귀엽습니다2",
    element: <TesterLogin2 />,
  },
]);

export default router;
