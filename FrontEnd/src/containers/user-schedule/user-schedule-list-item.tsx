import styled from "styled-components";
import { DefaultButton } from "@shared/button";

const Wrapper = styled.div`
  /* display:flex; */
  /* justify-content:center; */
  /* align-items: center; */
  height:10rem;
  background-color:lightpink;
  margin-bottom: 1%;
  border-radius: 5px;
`;
const DataWrapper = styled.div`
  height:100%;
  width:100%;
  display:flex;
  justify-content:center;
  align-items: center;
  flex-direction:row;
`;

const TimeWrapper = styled.div`
  flex:3;
  display:flex;
  flex-direction:column;
  align-items: center;
`;

const PtInfoWrapper = styled.div`
  flex:6;
  display:flex;
  flex-direction:column;
  align-items:center;
`;

const TrainerName = styled.div`
`;

const ButtonWrapper = styled.div`
  flex:3;
  display:flex;
  justify-content: center;

`;




interface ScheduleListItemProps {
  data:{
    trName: string;
    ptName:string;
    ptDate:string;
    ptStartTime:string;
  };
}




function UserScheduleListItem(props: ScheduleListItemProps) {
  return (
    <Wrapper>
      <DataWrapper>
        <TimeWrapper> <h3>Time: {props.data.ptStartTime}</h3></TimeWrapper>
        <PtInfoWrapper>
          <h2 style={{marginTop:"2px", marginBottom:"2px"}}>{props.data.ptName}</h2>
          <TrainerName>
            <b style={{fontSize:"5px"}}>트레이너</b>
            <b style={{fontSize:"1rem"}}> {props.data.trName}</b>
          </TrainerName>
        </PtInfoWrapper>
        <ButtonWrapper>
          <DefaultButton> 리뷰쓰기 </DefaultButton>
        </ButtonWrapper>
      </DataWrapper>
    </Wrapper>
  );
}

// function UserScheduleListItem() {
//   //No Axios 테스트용 
//     return (
//     <Wrapper>
//       <DataWrapper>
//         <TimeWrapper> <h2>Time</h2></TimeWrapper>
//         <PtInfoWrapper>
//           PTINFO
//         </PtInfoWrapper>

//       </DataWrapper>
//     </Wrapper>
//       );
//   }

  export default UserScheduleListItem;
  