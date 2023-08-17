import { useOpenvidu } from "../hooks/use-openvidu";
import LiveItem from "../containers/pt-room/live-item";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { useLocation } from "react-router-dom";
import styled from "styled-components";

const MyVideo = styled.div`
  width: 20%;
`;

const OtherVideo = styled.div`
  width: 50%;
`;

const PtRoom = () => {
  const user = useSelector((state: RootState) => state.profile.profileId);
  const liveData = useLocation().state;

  const { publisher, streamList, onChangeCameraStatus, onChangeMicStatus } =
    useOpenvidu(user, liveData.liveId);

  return (
    <div>
      {publisher && (
        <div>
          <MyVideo>
            {streamList && (
              <LiveItem
                stream={streamList[0].streamManager}
                profileId={streamList[0].profileId}
              />
            )}
          </MyVideo>
          {streamList &&
            streamList.map(
              (stream, index) =>
                stream.profileId !== user && (
                  <LiveItem
                    stream={stream.streamManager}
                    profileId={stream.profileId}
                  />
                )
            )}
        </div>
      )}
    </div>
  );
};

export default PtRoom;
