package com.tcha.chatting.dto;

import jakarta.persistence.Id;
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
public class ChannelMessage {

    private Long channelId;
    private MessageType type;
    private String sender; // 보낸 사람
    private String message; // 메세지 내용


    public enum MessageType {
        ENTER, TALK;
//        CONNECT, TALK, DISCONNECT;
    }
}
