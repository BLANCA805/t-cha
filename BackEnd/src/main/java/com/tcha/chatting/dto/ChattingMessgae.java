package com.tcha.chatting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChattingMessgae {


    //    private Long channelId;
    private MessageType type;
    private String sender; // 보낸 사람
    private String context; // 메세지 내용

    //현재 필요하다고 생각되는 enum값: 방만들기, 입장, 퇴장, 채팅 보내기
    public enum MessageType {
        ENTER, TALK;
//        CONNECT, TALK, DISCONNECT;
    }
}
