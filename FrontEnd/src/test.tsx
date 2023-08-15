import React from "react";
import { useOpenvidu } from "./hooks/use-openvidu";

const Test = () => {
  const { publisher, streamList, onChangeCameraStatus, onChangeMicStatus } =
    useOpenvidu(1, 1);

  return (
    <div>
      {publisher && (
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
      )}
    </div>
  );
};

export default Test;
