import styled from "styled-components";
import { DefaultButton } from "@shared/button";

interface ProfileImgProps{
    src:string;
    alt:string;
    width?:number;
    height?:number;
}

interface BookedTrainerListItemProps{
    data:{
        id: number;
        name: string;
        keywordTags: string[];
        profileImg?: ProfileImgProps | any;
    };
}

const Wrapper = styled.div`
    display:flex;
    flex-direction: row;
    height:15rem;
    background-color: ${({ theme }) => theme.color.light};
    border-radius: 10px;
    margin-bottom: 0.5%;

`;

const PhotoWrapper = styled.div`
    flex:3;
    display:flex;
    justify-content: center;
    align-items: center;
`;

const TRimg = styled.img`
    height:12rem;
    width:12rem;
    border-radius: 50%;
    background-color: lightgray;
`;

const DataWrapper = styled.div`
    flex:6;
    display:flex;
    flex-direction: column;
    justify-content: center;
    margin-bottom:2%;
    
`;

const BookmarkWrapper = styled.div`
    flex:3;
    display:flex;
    justify-content: center;
    align-items: center;
`;

const NameWrapper = styled.div`
    margin-bottom:1rem;
    margin-left:3%;
`;

const KeywordWrapper = styled.div`
    margin-left:3%;
`;

function BookedTrainerListItem(props: BookedTrainerListItemProps){

    return(
        <Wrapper>
            <PhotoWrapper>
                <TRimg />
            </PhotoWrapper>
            <DataWrapper>
                <NameWrapper>
                    <b style={{fontSize:"3.5rem"}}> {props.data.name}</b>
                    <b style={{fontSize:"2.5rem", marginLeft:"1rem"}}>트레이너</b>
                </NameWrapper>
                <KeywordWrapper>
                    {props.data.keywordTags.map((tag, index) => (
                        <b style={{fontSize:"1.5rem", marginLeft:"1%"}}> #{tag}</b>
                    ))}        
                </KeywordWrapper>
            </DataWrapper>
            <BookmarkWrapper>
                {/* 북마크 해제 버튼 클릭시 list에서 delete되는 로직 구현필요  */}
                <DefaultButton>북마크해제</DefaultButton>
            </BookmarkWrapper>
        </Wrapper>
    );
}

export default BookedTrainerListItem;