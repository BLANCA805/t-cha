import { useDispatch, useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { useLocation } from "react-router-dom";
import { useRef, useEffect, useState } from "react";
import { OpenVidu, Publisher, Session } from "openvidu-browser";
import {
  clearItems,
  setOV,
  setOpenViduToken,
  setSessionId,
} from "src/redux/slicers";
import axios from "axios";
import { PtLiveData } from "@user-trainer/trainer-schedule";
import {
  checkSession,
  connectSession,
  createSession,
  createToken,
} from "src/hooks/use-openvidu";

const OPENVIDU_SERVER_URL = "https://www.tcha.site:8443/";
const OPENVIDU_SERVER_SECRET = "blanca05";

function PtRoom() {
  // const videoRef = useRef<HTMLVideoElement>(null)
  const userId = useSelector((state: RootState) => state.profile.id);
  const userName = useSelector((state: RootState) => state.profile.name);
  const userProfileImage = useSelector(
    (state: RootState) => state.profile.profileImage
  );

  const ptLiveData: PtLiveData = useLocation().state;
  const [publisher, setPublisher] = useState<Publisher | null>(null);
  const [session, setSession] = useState<Session | null>(null);
  const dispatch = useDispatch();

  checkSession(3).then((response) => {
    if (response === "exist") {
      createToken(3).then((response) => {
        console.log("토큰 생성 완료");
        dispatch(setOpenViduToken(response));
      });
    } else if (response === "none") {
      createSession(3).then((response) => {
        console.log("세션 생성 완료");
        dispatch(setSessionId(response));
        createToken(3).then((response) => {
          console.log("토큰 생성 완료");
          dispatch(setOpenViduToken(response));
        });
      });
    }
  });

  
  const userOpenViduToken = useSelector(
    (state: RootState) => state.ptLive.userOpenViduToken
    );
    
  useEffect(() => {
    connectSession(userOpenViduToken).then((response) => {
      setPublisher(response.publisher)
      setSession(response.session)
    })
  })  

  const test = () => {
    console.log(publisher)
  };

  const check = () => {
    axios
      .get(OPENVIDU_SERVER_URL + "openvidu/api/sessions/", {
        headers: {
          Authorization:
            "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
        },
      })
      .then((response) => {
        console.log(response);
      });
  };

  return (
    <div>
      <button onClick={test}>테스트용 버튼</button>
      <button onClick={check}>확인하기</button>
      <div>
        <video
          autoPlay
          ref={(videoRef) => {
            if (videoRef && publisher && publisher.stream) {
              videoRef.srcObject = publisher.stream.getMediaStream();
            }
          }}
        ></video>
        {/* <button onClick={() => onChangeCameraStatus}>화면 조절</button>
          <button onClick={() => onChangeMicStatus}>마이크 조절</button> */}
      </div>
      {/* )} */}
    </div>
  );
}

export default PtRoom;
