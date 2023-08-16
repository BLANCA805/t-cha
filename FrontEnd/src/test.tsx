import React, { useRef, useState, useCallback, useEffect } from "react";
import styled from "styled-components";
import { Button, IconButton } from "@mui/material";
import BrushIcon from "@mui/icons-material/Brush";

interface Coordinate {
  x: number;
  y: number;
}

const ButtonContainer = styled.div`
  display: flex;
  margin: 1rem;
  justify-content: center;
`;

function Test() {
  const canvasRef = useRef<HTMLCanvasElement>(null);

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
    setImage(
      "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA2MjhfNzMg%2FMDAxNjg3ODg0MTUwNzMy.N-DRMWPIHW3ru3kQT8M7Id_JhjNNo_Bjg9Qm91RBmicg.EEKbqawwFxUQ3JixeBSNdk7DR5nwCLc7ZBQQ0okPPlIg.JPEG.omaro1999%2Foutput_1894273757.jpg&type=sc960_832"
    );
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
        context.drawImage(image, 0, 0, canvas.width, canvas.height);
      };
    }
  };

  useEffect(() => {
    setImage(
      "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA2MjhfNzMg%2FMDAxNjg3ODg0MTUwNzMy.N-DRMWPIHW3ru3kQT8M7Id_JhjNNo_Bjg9Qm91RBmicg.EEKbqawwFxUQ3JixeBSNdk7DR5nwCLc7ZBQQ0okPPlIg.JPEG.omaro1999%2Foutput_1894273757.jpg&type=sc960_832"
    );
  }, []);

  return (
    <>
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
      </ButtonContainer>
      <canvas ref={canvasRef} height="750" width="750"></canvas>
    </>
  );
}

export default Test;
