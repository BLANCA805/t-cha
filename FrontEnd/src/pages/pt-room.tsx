import { useSelector } from "react-redux";
import LiveItem from "src/containers/pt-room/live-item";
import { RootState } from "src/redux/store";

function PtRoom() {
  const user = useSelector((state: RootState) => state.profile.profileId);
  return <div>{/* <LiveItem profileId={user}></LiveItem> */}</div>;
}

export default PtRoom;
