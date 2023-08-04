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
public class ChattingMessage {

    //    private String type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private Object message; // 메시지
    private MessageType type;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void newConnect() {
        this.type = MessageType.OPEN;
    }

    public void closeConnect() {
        this.type = MessageType.CLOSE;
    }

    public enum MessageType {
        ENTER, TALK, JOIN, OPEN, CLOSE;
//        CONNECT, TALK, DISCONNECT;
    }
}