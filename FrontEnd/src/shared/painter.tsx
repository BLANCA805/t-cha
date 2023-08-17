import React, { useRef, useState, useCallback, useEffect } from "react";
import styled from "styled-components";
import { Button, IconButton } from "@mui/material";
import BrushIcon from "@mui/icons-material/Brush";
import dayjs from "dayjs";

interface Coordinate {
  x: number;
  y: number;
}

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
`;

const ButtonContainer = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-evenly;
  margin-top: 2rem;
`;

function Painter({ capturedImage }: { capturedImage: string }) {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    // const canvas = document.querySelector("[canvas]");
    // if (canvas) {
    //   canvas.setAttribute("width", (window.innerWidth * 0.25).toString());
    //   canvas.setAttribute("height", (window.innerHeight * 0.34).toString());
    // }
    const canvas = canvasRef.current;
    if (canvas) {
      canvas.width = window.innerWidth * 0.25;
      canvas.height = window.innerHeight * 0.34;
    }
  }, []);

  const nowTime = dayjs();

  const [mousePosition, setMousePosition] = useState<Coordinate | undefined>(
    undefined
  );
  const [isPainting, setIsPainting] = useState(false);

  const [strokeColor, setStrokeColor] = useState("red");

  const getCoordinates = (event: MouseEvent): Coordinate | undefined => {
    if (!canvasRef.current) {
      return;
    }

    const canvas: HTMLCanvasElement = canvasRef.current;
    return {
      x: event.pageX - canvas.offsetLeft,
      y: event.pageY - canvas.offsetTop,
    };
  };

  const drawLine = (
    originalMousePosition: Coordinate,
    newMousePosition: Coordinate
  ) => {
    if (!canvasRef.current) {
      return;
    }
    const canvas: HTMLCanvasElement = canvasRef.current;
    const context = canvas.getContext("2d");

    if (context) {
      context.strokeStyle = strokeColor;
      context.lineJoin = "round";
      context.lineWidth = 5;

      context.beginPath();
      context.moveTo(originalMousePosition.x, originalMousePosition.y);
      context.lineTo(newMousePosition.x, newMousePosition.y);
      context.closePath();

      context.stroke();
    }
  };

  const setStrokeStyle = (color: string) => {
    setStrokeColor(color);
  };

  const startPaint = useCallback((event: MouseEvent) => {
    const coordinates = getCoordinates(event);
    if (coordinates) {
      setIsPainting(true);
      setMousePosition(coordinates);
    }
  }, []);

  const paint = useCallback(
    (event: MouseEvent) => {
      event.preventDefault();
      event.stopPropagation();

      if (isPainting) {
        const newMousePosition = getCoordinates(event);
        if (mousePosition && newMousePosition) {
          drawLine(mousePosition, newMousePosition);
          setMousePosition(newMousePosition);
        }
      }
    },
    [isPainting, mousePosition]
  );

  const exitPaint = useCallback(() => {
    setIsPainting(false);
  }, []);

  useEffect(() => {
    if (!canvasRef.current) {
      return;
    }
    const canvas: HTMLCanvasElement = canvasRef.current;

    canvas.addEventListener("mousedown", startPaint);
    canvas.addEventListener("mousemove", paint);
    canvas.addEventListener("mouseup", exitPaint);
    canvas.addEventListener("mouseleave", exitPaint);

    return () => {
      canvas.removeEventListener("mousedown", startPaint);
      canvas.removeEventListener("mousemove", paint);
      canvas.removeEventListener("mouseup", exitPaint);
      canvas.removeEventListener("mouseleave", exitPaint);
    };
  }, [startPaint, paint, exitPaint]);

  const clearPaint = () => {
    if (!canvasRef.current) {
      return;
    }
    const canvas: HTMLCanvasElement = canvasRef.current;
    const context = canvas.getContext("2d");

    context?.clearRect(0, 0, canvas.width, canvas.height);
    setImage(capturedImage);
    context?.beginPath();
  };

  const setImage = (imageUrl: string) => {
    if (!canvasRef.current) {
      return;
    }
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");

    if (context) {
      const image = new Image();
      image.src = imageUrl;
      image.onload = () => {
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.save(); // Save the current context state
        context.scale(-1, 1); // Apply horizontal flip
        context.drawImage(image, -canvas.width, 0, canvas.width, canvas.height); // Draw flipped image
        context.restore(); // Restore the context state
      };
    }
  };

  useEffect(() => {
    if (capturedImage) {
      setImage(capturedImage);
      console.log("캡처");
    }
  }, [capturedImage]);

  function dataURLtoBlob(dataURL: string) {
    const parts = dataURL.split(";base64,");
    const contentType = parts[0].split(":")[1];
    const byteCharacters = atob(parts[1]);
    const byteArrays = [];
    for (let offset = 0; offset < byteCharacters.length; offset += 512) {
      const slice = byteCharacters.slice(offset, offset + 512);
      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }
    return new Blob(byteArrays, { type: contentType });
  }

  function downloadImage() {
    if (canvasRef.current) {
      const canvas = canvasRef.current;
      const imageData = canvas.toDataURL("image/png");

      const blob = dataURLtoBlob(imageData);
      const a = document.createElement("a");
      a.href = URL.createObjectURL(blob);
      a.download = `${nowTime.format("YYYY-MM-DD_HH-mm-ss")}`;
      a.click();
    }
  }

  return (
    <Wrapper>
      <ButtonContainer>
        <Button
          style={{
            color: "white",
            backgroundColor: "black",
          }}
          onClick={clearPaint}
        >
          전체 지우기
        </Button>
        <IconButton
          style={{ color: "white", backgroundColor: "red" }}
          onClick={() => setStrokeStyle("red")}
        >
          {" "}
          <BrushIcon />
        </IconButton>
        <IconButton
          style={{ color: "white", backgroundColor: "blue" }}
          onClick={() => setStrokeStyle("blue")}
        >
          {" "}
          <BrushIcon />
        </IconButton>
        <IconButton
          style={{ color: "white", backgroundColor: "#fff200" }}
          onClick={() => setStrokeStyle("yellow")}
        >
          {" "}
          <BrushIcon />
        </IconButton>
        <Button
          style={{
            color: "white",
            backgroundColor: "gray",
          }}
          onClick={downloadImage}
        >
          이미지 저장
        </Button>
      </ButtonContainer>
      <canvas ref={canvasRef} style={{ marginTop: "2rem" }}></canvas>
    </Wrapper>
  );
}

export default Painter;
