import { createBrowserRouter } from "react-router-dom";

import App from "./App";

import Home from "./Home";

import Auth from "./screens/auth/Auth";

import ChatList from "./screens/chat/chatlist/ChatList";
import Chatting from "./screens/chat/chatting/Chatting";

import CustomerCenter from "./screens/customercenter/CustomerCenter";
import Notice from "./screens/customercenter/notice/Notice";
import QnA from "./screens/customercenter/qna/QnA";

import MyPage from "./screens/mypage/MyPage";
import BookMark from "./screens/mypage/pages/bookmark/BookMark";
import Calendar from "./screens/mypage/pages/calendar/Calendar";
import InfoEdit from "./screens/mypage/pages/infoedit/InfoEdit";
import Payment from "./screens/mypage/pages/payment/Payment";
import MyReview from "./screens/mypage/pages/review/MyReview";

import PtList from "./screens/personaltrainer/PtList";
import PtDetail from "./screens/personaltrainer/ptdetail/PtDetail";
import PtInfo from "./screens/personaltrainer/ptdetail/info/PtInfo";
import PtReview from "./screens/personaltrainer/ptdetail/review/PtReview";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "",
        element: <Home />,
      },
      {
        path: "auth",
        element: <Auth />,
      },
      {
        path: "chat",
        element: <ChatList />,
        children: [
          {
            path: "chatid",
            element: <Chatting />,
          },
        ],
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
            path: "qna",
            element: <QnA />,
          },
        ],
      },
      {
        path: "userid",
        element: <MyPage />,
      },
      {
        path: "userid/bookmark",
        element: <BookMark />,
      },
      {
        path: "userid/calendar",
        element: <Calendar />,
      },
      {
        path: "userid/info-edit",
        element: <InfoEdit />,
      },
      {
        path: "userid/payment",
        element: <Payment />,
      },
      {
        path: "userid/my-review",
        element: <MyReview />,
      },
      {
        path: "pt",
        element: <PtList />,
      },
      {
        path: "pt/trainerid",
        element: <PtDetail />,
        children: [
          {
            path: "trainer-info",
            element: <PtInfo />,
          },
          {
            path: "trainer-review",
            element: <PtReview />,
          },
        ],
      },
    ],
  },
]);

export default router;
