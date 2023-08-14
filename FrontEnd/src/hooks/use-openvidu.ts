import { useCallback, useEffect, useMemo, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { type RootState } from "../redux/store";
import { Session, OpenVidu, StreamManager } from "openvidu-browser";

import axios from "axios";

const OPENVIDU_SERVER_URL = "https://www.tcha.site:8443/";
const OPENVIDU_SERVER_SECRET = "blanca05";
const OV = new OpenVidu();



// pt 라이브 정보 (ptLive 컨트롤러에 추가하기)
export interface PtLiveData {
  trainerId: string; // 트레이너의 유저 아이디
  ptudentId: string;
  liveId: number;
  status: string; // pt 라이브 상태 (입장 불가, 진행 중, 종료 가능, 종료)
}


// 라이브 입장 (ptLiveId를 통해) -> getPtLive에 대한 response
export function EnterPtLive(ptLiveData: PtLiveData) {

  // 현재 로그인한 유저 토큰 가져오기
  const userId = useSelector((state: RootState) => state.auth.token);

  // 권한 확인
  if (ptLiveData.ptudentId === userId || ptLiveData.trainerId === userId) { // 현재 유저가 접근 가능한 라이브
    if (ptLiveData.status === "진행 중") { // 입장 가능한 상태의 라이브
      createSession(String(ptLiveData.liveId));
    } else {
      console.log("입장 가능한 시간이 아님")
    }
  } else {
    console.log("현재 로그인한 유저에게 입장 권한이 없는 PT 수업")
  }
}

export function getToken(liveId: string): Promise<any> {
  return createSession(liveId).then((liveId) => createToken(liveId));
}

function createSession(liveId: string): Promise<any> {
  const data = JSON.stringify({ customSessionId: liveId });
  return new Promise((resolve, reject) => {
    axios
      .post(OPENVIDU_SERVER_URL + "openvidu/api/sessions", data, {
        headers: {
          Authorization: "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        console.log("CREATE SESSION", response);
        resolve(response.data.id);
      })
      .catch((response) => {
        var error = Object.assign({}, response);
        if (error?.response?.status === 409) {
          resolve(liveId);
        } else {
          console.log(error);
          console.warn(
            "No connection to OpenVidu Server. This may be a certificate error at " +
            OPENVIDU_SERVER_URL,
          );
          // window.location.assign(OPENVIDU_SERVER_URL + "accept-certificate");
        }
      });
  });
}

function createToken(liveId: string): Promise<any> {
  return new Promise((resolve, reject) => {
    const data = {};
    axios
      .post(
        OPENVIDU_SERVER_URL + "openvidu/api/sessions/" + liveId + "/connection", data, {
          headers: {
            Authorization: "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
            "Content-Type": "application/json",
          },
        }
      )
      .then((response) => {
        console.log("TOKEN", response);
        resolve(response.data.token);
      })
      .catch((error) => reject(error));
  });
}

export const useOpenvidu = (profileId: number, liveId: number) => {
  const [subscribers, setSubscribers] = useState<any[]>([]);
  const [publisher, setPublisher] = useState<any>();
  const [session, setSession] = useState<any>();

  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect();
    }
    setSession(null);
    setPublisher(null);
    setSubscribers([]);
  }, [session]);

  useEffect(() => {
    const openVidu = new OpenVidu();
    const session = openVidu.initSession();

    session.on("streamCreated", (event) => {
      const subscriber = session.subscribe(event.stream, "");
      const data = JSON.parse(event.stream.connection.data);
      setSubscribers((prev) => {
        return [
          ...prev.filter((it) => it.profileId !== +data.profileId),
          {
            streamManager: subscriber,
            profileId: +data.profileId,
          },
        ];
      });
    });

    session.on("streamDestroyed", (event) => {
      event.preventDefault();

      const data = JSON.parse(event.stream.connection.data);
      setSubscribers((prev) =>
        prev.filter((it) => it.profileId !== +data.profileId)
      );
    });

    session.on("exception", (exception) => {
      console.warn(exception);
    });

    getToken(String(liveId)).then((token) => {
      session!
        .connect(token, JSON.stringify({ profileId }))
        .then(async () => {
          await navigator.mediaDevices.getUserMedia({
            audio: true,
            video: true,
          });
          const devices = await openVidu.getDevices();
          const videoDevices = devices.filter(
            (device) => device.kind === "videoinput"
          );

          const publisher = openVidu.initPublisher("", {
            audioSource: undefined,
            videoSource: videoDevices[0].deviceId,
            publishAudio: true,
            publishVideo: true,
            resolution: "640x480",
            frameRate: 30,
            insertMode: "APPEND",
            mirror: false,
          });

          setPublisher(publisher);
          session.publish(publisher);
        })
        .catch((error) => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });
    });

    setSession(session);
    return () => {
      if (session) {
        session.disconnect();
      }
      setSession(null);
      setPublisher(null);
      setSubscribers([]);
    };
  }, [liveId, profileId]);

  useEffect(() => {
    window.addEventListener("beforeunload", () => leaveSession());
    return () => {
      window.removeEventListener("beforeunload", () => leaveSession());
    };
  }, [leaveSession]);

  const onChangeCameraStatus = useCallback(
    (status: boolean) => {
      publisher?.publishVideo(status);
    },
    [publisher]
  );

  const onChangeMicStatus = useCallback(
    (status: boolean) => {
      publisher?.publishAudio(status);
    },
    [publisher]
  );

  const streamList = useMemo(
    () => [{ streamManager: publisher, profileId }, ...subscribers],
    [publisher, subscribers, profileId]
  );

  return {
    publisher,
    streamList,
    onChangeCameraStatus,
    onChangeMicStatus,
  };
};
