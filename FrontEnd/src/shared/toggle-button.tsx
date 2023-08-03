import * as React from "react";
import ToggleButton from "@mui/material/ToggleButton";
import ToggleButtonGroup from "@mui/material/ToggleButtonGroup";

export default function ToggleButtons(props: {
  tabs: { text: string; name: string }[];
  width: string;
  clickTab: (name: string) => void;
}) {
  const [tab, setTab] = React.useState<string>(`${props.tabs[0].name}`);

  const handleTab = (
    event: React.MouseEvent<HTMLElement>,
    clickedTab: string
  ) => {
    setTab(clickedTab);
  };

  return (
    <ToggleButtonGroup
      value={tab}
      exclusive
      onChange={handleTab}
      style={{ width: props.width }}
    >
      {props.tabs.map((tab: { text: string; name: string }, index) => (
        <ToggleButton
          value={tab.name}
          onClick={() => props.clickTab(tab.name)}
          style={{
            width: "100%",
          }}
          key={index}
        >
          {tab.text}
        </ToggleButton>
      ))}
    </ToggleButtonGroup>
  );
}
