import styled from "styled-components";
import { useStream } from "../../hooks/use-stream";

const Video = styled.video`
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg);
`;

const LiveItem = ({
  stream,
  profileId,
}: {
  stream: any;
  profileId: number;
}) => {
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);
  console.log(stream);

  const { videoRef, speaking, micStatus, videoStatus } = useStream(stream);

  return (
    <div>
      <Video ref={videoRef} style={{ width: "100%", height: "100%" }}></Video>
    </div>
  );
};

export default LiveItem;
