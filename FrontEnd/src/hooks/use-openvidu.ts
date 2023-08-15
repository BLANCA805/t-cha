import { useCallback, useEffect, useMemo, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { type RootState } from "../redux/store";
import { Session, OpenVidu, StreamManager } from "openvidu-browser";

import axios from "axios";

const OPENVIDU_SERVER_URL = "https://www.tcha.site:8443/";
const OPENVIDU_SERVER_SECRET = "blanca05";


export interface PtLiveData {
  ptClassId: number;
  ptLiveId: number;
  status: string;
  trainerId: string;
  trainerName: string;
  trainerProfileImage: string;
  userId: string;
  userName: string;
  userProfileImage: string;
}

// 생성된 오픈비두 라이브에 대한 토큰 발급(트레이너, 유저 각각 따로)
export function getToken(ptLive: PtLiveData): Promise<any> {
  return enterPtLive(ptLive.ptLiveId).then((liveId) => createToken(liveId));
}

function enterPtLive(liveId: number): Promise<any> {
  return new Promise((resolve, reject) => {
    axios
      .post(OPENVIDU_SERVER_URL + "openvidu/api/sessions", String(liveId), {
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
  const userId = useSelector((state: RootState) => state.auth.token);
  const userName = useSelector((state: RootState) => state.profile.name);
  const userProfileImage = useSelector((state: RootState) => state.profile.profileId); // 사진으로 바꾸기

  return new Promise((resolve, reject) => {
    const data = {
      id: userId,
      name: userName,
      profileImage: userProfileImage,
    };
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

export const useOpenvidu = (userId: string, liveId: number) => {
  const [publisher, setPublisher] = useState<any>();
  const [session, setSession] = useState<any>();

  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect();
    }
    setSession(null);
    setPublisher(null);
  }, [session]);

  useEffect(() => {
    const OV = new OpenVidu();
    const session = OV.initSession();

    session.on("streamCreated", (event: any) => {
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
          const devices = await OV.getDevices();
          const videoDevices = devices.filter(
            (device) => device.kind === "videoinput"
          );

          const publisher = OV.initPublisher("", {
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
