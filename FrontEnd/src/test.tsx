// import {useOpenvidu } from "./hooks/use-openvidu";
import { useSelector } from "react-redux";
import { type RootState } from "./redux/store";


const Test = () => {
  // const profileId = useSelector((state: RootState) => state.profile.profileId);
  // const { publisher, onChangeCameraStatus, onChangeMicStatus } =
  //   useOpenvidu(profileId, 1);

  // const ptLiveId = createPtLive(1).sessionId;

  return (
    <div>
      {/* {publisher && (
        <div>
          <video
            ref={(videoRef) => {
              if (videoRef && publisher && publisher.stream) {
                videoRef.srcObject = publisher.stream.getMediaStream();
              }
            }}
            autoPlay
            muted
          />
          <button onClick={() => onChangeCameraStatus}>화면 조절</button>
          <button onClick={() => onChangeMicStatus}>마이크 조절</button>
        </div>
      )} */}
    </div>
  );
};

export default Test;
