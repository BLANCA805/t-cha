import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { useLocation } from "react-router-dom";
import { EnterPtLive, useOpenVidu } from "src/hooks/use-openvidu";
import { useRef, useEffect } from "react";


function PtRoom() {
  const userId = useSelector((state: RootState) => state.auth.token);
  const liveData = useLocation().state
  // const { session, publisher, streamManager } = EnterPtLive(userId, liveData.liveId);
  const videoRef = useRef<HTMLVideoElement>(null);
  // console.log(publisher);
  // const { publisher, streamList, onChangeCameraStatus, onChangeMicStatus } =
  //   useOpenVidu(userId, liveData.liveId);


  // useEffect(() => {
    // if (streamManager && !!videoRef.current) {
    //   streamManager?.addVideoElement(videoRef.current);
    // }
  // }, []);

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
