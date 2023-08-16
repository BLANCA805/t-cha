import { useDispatch, useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { useLocation } from "react-router-dom";
import { useRef, useEffect } from "react";
import { OpenVidu, Publisher, Session } from "openvidu-browser";
import { setOV, setOpenViduToken, setSessionId } from "src/redux/slicers";
import axios from "axios";
import { PtLiveData } from "@user-trainer/trainer-schedule";

const OPENVIDU_SERVER_URL = "https://www.tcha.site:8443/";
const OPENVIDU_SERVER_SECRET = "blanca05";

function PtRoom() {
  // 현재 로그인한 유저 정보
  const userId = useSelector((state: RootState) => state.auth.token);
  const userName = useSelector((state: RootState) => state.profile.name);
  const userProfileImage = useSelector(
    (state: RootState) => state.profile.profileImage
  );
  const userOpenViduToken = useSelector(
    (state: RootState) => state.ptLive.userOpenViduToken
  );

  const ptLiveData: PtLiveData = useLocation().state;
  const videoRef = useRef<HTMLVideoElement>(null);
  const dispatch = useDispatch();

  // openvidu 설정, 최초 입장 시 한 번만
  useEffect(() => {
    const ov = new OpenVidu();
    dispatch(setOV({ ov }));
    enterSession(ptLiveData.ptLiveId);
  }, []);

  // session 입장
  const enterSession = (ptLiveId: number) => {
    const OV = new OpenVidu();
    const data = JSON.stringify({ customSessionId: ptLiveId });
    // 이미 존재하는 session인지 확인
    // console.log(ptLiveId);
    axios
      .get(OPENVIDU_SERVER_URL + "openvidu/api/sessions/" + ptLiveId, {
        headers: {
          Authorization:
            "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
        },
      })
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          // 이미 존재하는 세션
          if (userOpenViduToken !== null) {
            // 현재 로그인한 유저가 이미 입장했던 세션(토큰이 존재)이라면 connect 하고 함수 종료
            response.data.connect(userOpenViduToken);
            return;
          }
          createConnection(ptLiveId);
        } else if (response.status === 404) {
          // 존재하지 않는 세션
          console.log("존재하지 않는 세션, 새로 생성하기");
          createSession(ptLiveId);
          createConnection(ptLiveId);
        }
        const session: Session = response.data;
        if (userOpenViduToken !== null) {
          session.connect(userOpenViduToken).then(async () => {
            await navigator.mediaDevices.getUserMedia({
              audio: true,
              video: true,
            });
            const newPublisher = await OV.initPublisher("", {
              audioSource: undefined,
              videoSource: undefined,
              publishAudio: true,
              publishVideo: true,
              resolution: "640x480",
              frameRate: 30,
              insertMode: "APPEND",
              mirror: true,
            });
            session.publish(newPublisher);
          });
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // session 생성
  function createSession(ptLiveId: number) {
    const data = JSON.stringify({ customSessionId: ptLiveId });
    axios
      .post(OPENVIDU_SERVER_URL + "openvidu/api/sessions/", data, {
        headers: {
          Authorization:
            "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        dispatch(setSessionId(response.data.id));
        console.log(response.data);
      });
  }

  // connection 생성 -> token 생성
  const createConnection = (ptLiveId: number) => {
    const data = JSON.stringify({
      id: userId,
      name: userName,
      image: userProfileImage,
    });
    axios
      .post(
        OPENVIDU_SERVER_URL +
          "openvidu/api/sessions/" +
          ptLiveId +
          "/connection",
        data,
        {
          headers: {
            Authorization:
              "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
            "Content-Type": "application/json",
          },
        }
      )
      .then((response) => {
        const token = response.data.token;
        dispatch(setOpenViduToken(token));
      })
      .catch((error) => console.log(error));
  };

  return (
    <div>
      {/* {streamManager && ( */}
      <div>
        <video autoPlay ref={videoRef}></video>
        {/* <button onClick={() => onChangeCameraStatus}>화면 조절</button>
          <button onClick={() => onChangeMicStatus}>마이크 조절</button> */}
      </div>
      {/* )} */}
    </div>
  );
}

export default PtRoom;
