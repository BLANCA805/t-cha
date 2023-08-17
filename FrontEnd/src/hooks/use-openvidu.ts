import { OpenVidu } from "openvidu-browser";
import { useCallback, useEffect, useMemo, useState } from "react";

import axios from "axios";

const OPENVIDU_SERVER_URL = "https://www.tcha.site:8443/";
const OPENVIDU_SERVER_SECRET = "blanca05";

export function getToken(liveId: string): Promise<any> {
  return createSession(liveId).then((liveId) => createToken(liveId));
}

export function createSession(sessionId: string): Promise<any> {
  const data = JSON.stringify({ customSessionId: sessionId });
  return new Promise((resolve, reject) => {
    axios
      .post(OPENVIDU_SERVER_URL + "openvidu/api/sessions", data, {
        headers: {
          Authorization:
            "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
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
          resolve(sessionId);
        } else {
          console.log(error);
          console.warn(
            "No connection to OpenVidu Server. This may be a certificate error at " +
              OPENVIDU_SERVER_URL
          );
          window.location.assign(OPENVIDU_SERVER_URL + "accept-certificate");
        }
      });
  });
}

export function createToken(liveId: string): Promise<any> {
  return new Promise((resolve, reject) => {
    const data = {};
    axios
      .post(
        OPENVIDU_SERVER_URL + "openvidu/api/sessions/" + liveId + "/connection",
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
            audio: false,
            video: true,
          });
          const devices = await openVidu.getDevices();
          const videoDevices = devices.filter(
            (device) => device.kind === "videoinput"
          );

          const publisher = openVidu.initPublisher("", {
            audioSource: undefined,
            videoSource: videoDevices[0].deviceId,
            publishAudio: false,
            publishVideo: true,
            resolution: "1280x720",
            frameRate: 60,
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
