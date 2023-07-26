import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

import styled from "styled-components";

const Container = styled.div`
  background-color: white;
  padding: 2%;
  border-radius: 10px;
  margin-top: 3%;
  margin-bottom: 3%;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
`;

interface TrainerInfoInterface {
  name: string;
}

function TrainerCard(prop: TrainerInfoInterface) {
  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardMedia
        sx={{ height: 140 }}
        image="/static/images/cards/contemplative-reptile.jpg"
        title="green iguana"
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {prop.name} 트레이너
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Lizards are a widespread group of squamate reptiles, with over 6,000
          species, ranging across all continents except Antarctica
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">Share</Button>
        <Button size="small">Learn More</Button>
      </CardActions>
    </Card>
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
  ];
  return (
    <Container>
      <h1>MD 추천 트레이너</h1>
      <Wrapper>
        {TrainerInfo.map((info, index) => (
          <div key={index}>
            <TrainerCard name={info.name}></TrainerCard>
          </div>
        ))}
      </Wrapper>
    </Container>
  );
}

export default RecommendTrainer;
