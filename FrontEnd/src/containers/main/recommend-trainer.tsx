import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

import styled from "styled-components";

const Wrapper = styled.div`
  width:100%;

`
const Container = styled.div`
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.light};
  padding: 2%;
  border-radius: 10px;
`;
// const ContainerTitle = styled.div`
//   /* font-family: 'jamsil3';
//   font-size: ${({ theme }) => theme.fontSize.md}; */
//   @media (max-width: 767px) {
//     max-height: 3rem;
//   }
// `

const CardWrapper = styled.div`
  display: flex;
  flex-direction:row;
  /* flex-wrap: wrap; */
  align-items: center;
`;

const StyledCard = styled(Card)`
  /* display:flex; */
  /* max-width:30%; */
  /* height: 140; */
  max-height:3.5rem;
  margin:5%;
  @media (max-width: 767px) {
    max-height: 3rem;
  }
  .typography{

  }
`;
const StyledCardMedia = styled(CardMedia)`
  display:flex !important;
  background-color: blue !important;
  /* @media (max-width: 767px) {
    height: 100px;
  } */
`;
const StyledCardContent = styled(CardContent)`
  /* height: 140px;
  @media (max-width: 767px) {
    height: 100px;
  } */
`;
const StyledCardActions = styled(CardActions)`
  /* height: 140px;
  @media (max-width: 767px) {
    height: 100px;
  } */
`;

const ContainerTitle = styled.h4`
  margin:2.5% 3% 2.5% 2% !important;
  font-size:2rem;
  @media (max-width: 767px) {
    margin :3% 2.5% !important;
    font-size : 1.2rem !important;
  }
`
const ContentsWrapper = styled.div`
  margin-bottom:1.5%;
`
const Context = styled.h6`
  display:flex;
  align-items: center;
  margin: 0% 3% !important;
  
  @media (max-width: 767px) {
    margin :0% 2% !important;
    font-size : 0.7rem !important;
  }
`


interface TrainerInfoInterface {
  name: string;
}

function TrainerCard(prop: TrainerInfoInterface) {
  return (
    <StyledCard sx={{ maxWidth: "100%" }}>
      <StyledCardMedia
        image="/static/images/cards/contemplative-reptile.jpg"
        title="green iguana"
      />
      <StyledCardContent>
        <Typography gutterBottom variant="h6" component="div">
          {prop.name} 트레이너
        </Typography>
        {/* <Typography variant="body2" color="text.secondary">
          Lizards are a widespread group of squamate reptiles, with over 6,000
          species, ranging across all continents except Antarctica
        </Typography> */}
      </StyledCardContent>
      {/* <CardActions>
        <Button size="small">Share</Button>
        <Button size="small">Learn More</Button>
      </CardActions> */}
    </StyledCard>
  );
}

function RecommendTrainer() {
  const TrainerInfo = [
    {
      name: "임병국1",
    },
    {
      name: "임병국2",
    },
    {
      name: "임병국3",
    },
    {
      name: "임병국4",
    },
    {
      name: "임병국5",
    },
  ];
  return (
    <Wrapper>
      <Container>
        <ContainerTitle>
          MD 추천 트레이너
        </ContainerTitle>
        <CardWrapper>
          {TrainerInfo.map((info, index) => (
            <div key={index}>
              <TrainerCard name={info.name}></TrainerCard>
            </div>
          ))}
        </CardWrapper>
      </Container>
    </Wrapper>
  );
}

export default RecommendTrainer;
