import { createBrowserRouter } from "react-router-dom";

import App from "./App";

import Home from "./Home";


import ChatList from "./screen/chat/chatlist/ChatList";
import Chatting from "./screen/chat/chatting/Chatting";

import CustomerCenter from "./screen/customercenter/CustomerCenter";
import Notice from "./screen/customercenter/notice/Notice";
import QnA from "./screen/customercenter/qna/QnA";

import MyPage from "./screen/mypage/MyPage";
import BookMark from "./screen/mypage/page/bookmark/BookMark";
import Calendar from "./screen/mypage/page/calendar/Calendar";
import InfoEdit from "./screen/mypage/page/infoedit/InfoEdit";
import Payment from "./screen/mypage/page/payment/Payment";
import MyReview from "./screen/mypage/page/review/MyReview";

import PtList from "./screen/personaltrainer/PtList";
import PtDetail from "./screen/personaltrainer/ptdetail/PtDetail";
import PtInfo from "./screen/personaltrainer/ptdetail/info/PtInfo";
import PtReview from "./screen/personaltrainer/ptdetail/review/PtReview";

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
