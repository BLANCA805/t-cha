import * as React from "react";
import { useNavigate } from "react-router-dom";
import ToggleButton from "@mui/material/ToggleButton";
import ToggleButtonGroup from "@mui/material/ToggleButtonGroup";

export default function ToggleButtons(props: {
  tabs: { text: string; path: string }[];
  width: string;
}) {
  const [tab, setTab] = React.useState<string | null>("left");

  const clickButton = useNavigate();

  const handleTab = (
    event: React.MouseEvent<HTMLElement>,
    newTab: string | null
  ) => {
    setTab(newTab);
  };

  return (
    <ToggleButtonGroup
      value={tab}
      exclusive
      onChange={handleTab}
      style={{ width: props.width }}
    >
      {props.tabs.map((tab: { text: string; path: string }) => (
        <ToggleButton
          value={tab.path}
          onClick={() => clickButton(`${tab.path}`)}
          style={{
            width: "100%",
          }}
        >
          {tab.text}
        </ToggleButton>
      ))}
    </ToggleButtonGroup>
  );
}
