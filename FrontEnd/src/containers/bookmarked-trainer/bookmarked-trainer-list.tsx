import { useState, useEffect } from "react";
import axios from "axios";
import BookedTrainerListItem from "./bookmarked-trainer-list-item";

import styled from "styled-components";
const Wrapper = styled.div`
`;

function BookedTrainerList(){
    const items=[
        {
            id: 1,
            name:"이채림",
            keywordTags: ["근력", "유산소", "필라테스"],
            profileImg:"",
        },
        {
            id: 2,
            name:"하정호",
            keywordTags: ["근력운동", "체력증진", "저녁반"],
            profileImg:"",
        },
        {
            id: 3,
            name:"변정원",
            keywordTags: ["근력", "자세교정", "새벽가능"],
            profileImg:"",
        },
    ];

    return(

        <Wrapper>
            {items.map((item) =>(
                <BookedTrainerListItem data={item}  />
            ))}
        </Wrapper>

    );
}

export default BookedTrainerList;