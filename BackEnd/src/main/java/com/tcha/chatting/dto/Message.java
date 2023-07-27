package com.tcha.chatting.dto;

import jakarta.persistence.Entity;
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
public class Message {

    @Id
    private String channelId;
    private MessageType type;
    private String sender; // 보낸 사람
    private Object data; // 메세지 내용


    public enum MessageType {
        CONNECT, TALK, DISCONNECT;
    }
}
