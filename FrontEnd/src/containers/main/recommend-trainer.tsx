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
const ContainerTitle = styled.div`
  /* font-family: 'jamsil3';
  font-size: ${({ theme }) => theme.fontSize.md}; */
  @media (max-width: 767px) {
    max-height: 3rem;
  }

`
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
          <h2>MD 추천 트레이너</h2>
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
