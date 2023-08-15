import React, { FC } from "react";
import { useOpenvidu } from "src/hooks/use-openvidu";
import { PtRoomData } from "src/interface";

const LiveItem: FC<PtRoomData> = ({ profileId, liveId }) => {
  const { publisher, streamList, onChangeCameraStatus, onChangeMicStatus } =
    useOpenvidu(profileId, liveId);

  const test = () => {
    console.log(streamList);
  };
  return (
    <div>
      <button onClick={test}></button>
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
          {/* publisher를 표시하거나 영상 통화 상태를 UI에 반영하는 등의 기능 추가 */}
        </div>
      )}
    </div>
  );
};

export default LiveItem;
