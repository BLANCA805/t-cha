import { useCallback, useEffect, useMemo, useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { type RootState } from "../redux/store";
import { Session, OpenVidu, StreamManager, Publisher } from "openvidu-browser";

import axios from "axios";

const OPENVIDU_SERVER_URL = "https://www.tcha.site:8443/";
const OPENVIDU_SERVER_SECRET = "blanca05";

export function createSession(ptLiveId: number): Promise<any> {
  return new Promise((resolve, reject) => {
    const data = JSON.stringify({ customSessionId: String(ptLiveId) });
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
        console.log(error);
        console.warn(
          "No connection to OpenVidu Server. This may be a certificate error at " +
            OPENVIDU_SERVER_URL
        );
      });
  });
}

export function createToken(ptLiveId: number): Promise<any> {
  return new Promise((resolve, reject) => {
    const data = {};
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
        resolve(response.data.token);
      })
      .catch((error) => reject(error));
  });
}

export function checkSession(ptLiveId: number): Promise<any> {
  return new Promise((resolve, reject) => {
    axios
      .get(OPENVIDU_SERVER_URL + "openvidu/api/sessions/" + ptLiveId, {
        headers: {
          Authorization:
            "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
        },
      })
      .then((response) => {
        if (response.status === 200) {
          resolve("exist");
        }
      })
      .catch((error) => {
        if (error.response && error.response.status === 404) {
          console.log("기존에 생성된 세션이 없습니다");
          resolve("none");
        } else {
          reject(error);
        }
      });
  });
}

export function connectSession(userOpenViduToken: string): Promise<any> {
  return new Promise((resolve, reject) => {

      const OV = new OpenVidu();
      const session = OV.initSession();
  
      session.connect(userOpenViduToken).then(async () => {
        await navigator.mediaDevices.getUserMedia({
          video: true,
          audio: true,
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
  
        session.publish(publisher);
        resolve({ publisher: publisher, session: session });
      });
    })

}

// export function EnterPtLive(userId: string, ptLiveId: number) {
//   const [publisher, setPublisher] = useState<Publisher[]>([]);
//   const [streamManager, setStreamManager] = useState<StreamManager>();
//   // const [session, setSession] = useState<any>();

//   // const leaveSession = useCallback(() => {
//   //   if (session) {
//   //     session.disconnect();
//   //   }
//   //   setSession(null);
//   //   setPublisher(null);
//   // }, [session]);

//   const OV = new OpenVidu();
//   const session = OV.initSession();

//   getToken(ptLiveId).then((token) => {
//     session!
//       .connect(token)
//       .then(async () => {
//         await navigator.mediaDevices.getUserMedia({
//           audio: true,
//           video: true,
//         });
//         const devices = await OV.getDevices();
//         // const videoDevices = devices.filter(
//         //   (device) => device.kind === "videoinput"
//         // );

//         const newPublisher = await OV.initPublisherAsync("", {
//           audioSource: undefined,
//           // videoSource: videoDevices[0].deviceId,
//           videoSource: undefined,
//           publishAudio: true,
//           publishVideo: true,
//           resolution: "640x480",
//           frameRate: 30,
//           insertMode: "APPEND",
//           mirror: true,
//         });

//         session.publish(newPublisher);
//         publisher.push(newPublisher);
//         setPublisher([...publisher]);
//       })
//       .catch((error) => {
//         console.log(
//           "There was an error connecting to the session:",
//           error.code,
//           error.message
//         );
//       });
//   });

//   session.on("streamCreated", (event: any) => {
//     // const data = JSON.parse(event.stream.connection.data);
//     console.log("streamCreated");
//   });

//   session.on("streamDestroyed", (event: any) => {
//     // event.preventDefault();
//     // const data = JSON.parse(event.stream.connection.data);
//     console.log("streamDestroyed");
//   });

//   session.on("exception", (exception) => {
//     console.warn(exception);
//   });

//   setStreamManager(publisher[0]);

//   // setSession(session);
//   // return () => {
//   //   if (session) {
//   //     session.disconnect();
//   //   }
//   //   setSession(null);
//   //   setPublisher(null);
//   // };

//   return { session, publisher, streamManager };
// }

export function useOpenVidu(userId: string, ptLiveId: number) {
  // useEffect(() => {
  //   const connectSession = async () => {
  //     await EnterPtLive(userId, ptLiveId);
  //   };
  //   connectSession();
  // }, []);
  // useEffect(() => {
  //   window.addEventListener("beforeunload", () => leaveSession());
  //   return () => {
  //     window.removeEventListener("beforeunload", () => leaveSession());
  //   };
  // }, [leaveSession]);
  // const onChangeCameraStatus = useCallback(
  //   (status: boolean) => {
  //     publisher?.publishVideo(status);
  //   },
  //   [publisher]
  // );
  // const onChangeMicStatus = useCallback(
  //   (status: boolean) => {
  //     publisher?.publishAudio(status);
  //   },
  //   [publisher]
  // );
  // const streamList = useMemo(
  //   () => [{ streamManager: publisher }],
  //   [publisher]
  // );
  // return {
  //   publisher,
  //   streamList,
  //   onChangeCameraStatus,
  //   onChangeMicStatus,
  // };
}
