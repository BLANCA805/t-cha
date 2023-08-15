import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
// import { useOpenvidu } from "src/hooks/use-openvidu";
import { useLocation } from "react-router-dom";


function PtRoom() {
  const userId = useSelector((state: RootState) => state.auth.token);
  const liveData = useLocation().state
  // const { publisher, streamList, onChangeCameraStatus, onChangeMicStatus } =
  //   useOpenvidu(userId, liveData.liveId);

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
}

export default PtRoom;
