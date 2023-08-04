package com.tcha.chatting.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChattingRoom {
    private String roomId;
    private String name;

    public static ChattingRoom create(String name) {
        ChattingRoom chattingRoom = new ChattingRoom();
        chattingRoom.roomId = UUID.randomUUID().toString();
        chattingRoom.name = name;
        return chattingRoom;
    }
}