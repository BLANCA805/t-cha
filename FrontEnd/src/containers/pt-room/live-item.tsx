import styled from "styled-components";
import { useStream } from "../../hooks/use-stream";

const Video = styled.video`
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg);
`;

const LiveItem = ({
  stream,
  profileId,
  onCapture,
}: {
  stream: any;
  profileId: number;
  onCapture: (imageDataUrl: string) => void;
}) => {
  const { videoRef, speaking, micStatus, videoStatus } = useStream(stream);

  const handleCaptureClick = () => {
    if (videoRef.current) {
      const canvas = document.createElement("canvas");
      canvas.width = videoRef.current.videoWidth;
      canvas.height = videoRef.current.videoHeight;

      const context = canvas.getContext("2d");
      if (context) {
        context.drawImage(videoRef.current, 0, 0, canvas.width, canvas.height);
        const imageDataUrl = canvas.toDataURL("image/png");
        onCapture(imageDataUrl);
      }
    }
  };

  return (
    <Video
      ref={videoRef}
      style={{ width: "95%", borderRadius: "1rem" }}
      onClick={handleCaptureClick}
    ></Video>
  );
};

export default LiveItem;
