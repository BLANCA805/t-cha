import { useOpenvidu } from "../hooks/use-openvidu";
import LiveItem from "../containers/pt-room/live-item";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { useLocation, useNavigate } from "react-router-dom";
import styled from "styled-components";
import Painter from "@shared/painter";
import { useState } from "react";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import { Button, IconButton } from "@mui/material";
import WriteExerciseLogInPtRoom from "@user-trainer/write-exercise-log-in-pt-room";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.color.secondary};
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const OtherVideo = styled.div`
  flex: 7;
  height: 97vh;
  margin: 1rem;
  background-color: ${({ theme }) => theme.color.light};
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 1rem;
`;

const LiveToolBox = styled.div`
  flex: 3;
  height: 97vh;
  margin: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
`;

const MyVideo = styled.div`
  background-color: ${({ theme }) => theme.color.light};
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 1rem;
  padding: 1rem;
`;

const PainterContainer = styled.div`
  border-radius: 1rem;
  background-color: ${({ theme }) => theme.color.light};
  width: 96%;
  height: 55%;
  margin-bottom: 1rem;
`;

const PtRoom = () => {
  const user = useSelector((state: RootState) => state.profile.profileId);
  const liveData = useLocation().state;

  const navigate = useNavigate();

  const [capturedImage, setCapturedImage] = useState<string>("");

  const { publisher, streamList, onChangeCameraStatus, onChangeMicStatus } =
    useOpenvidu(user, liveData);

  const onCapture = (image: string) => {
    setCapturedImage(image);
  };

  function test() {
    console.log(streamList);
  }

  return (
    <>
      {publisher && (
        <Wrapper>
          <button onClick={test}>Test</button>
          <OtherVideo>
            {streamList &&
              streamList.map(
                (stream, index) =>
                  stream.profileId !== user && (
                    <LiveItem
                      stream={stream.streamManager}
                      profileId={stream.profileId}
                      onCapture={onCapture}
                    />
                  )
              )}
          </OtherVideo>
          <LiveToolBox>
            <PainterContainer>
              <Painter capturedImage={capturedImage} />
              <WriteExerciseLogInPtRoom ptLiveId={liveData} />
            </PainterContainer>
            <MyVideo>
              {streamList && (
                <LiveItem
                  stream={streamList[0].streamManager}
                  profileId={streamList[0].profileId}
                  onCapture={onCapture}
                />
              )}
            </MyVideo>
            <div
              style={{
                display: "flex",
                width: "95%",
                marginTop: "1rem",
                justifyContent: "end",
              }}
            >
              <Button
                variant="contained"
                endIcon={<ExitToAppIcon />}
                onClick={() => navigate(-1)}
              >
                퇴장하기
              </Button>
            </div>
          </LiveToolBox>
        </Wrapper>
      )}
    </>
  );
};

export default PtRoom;
