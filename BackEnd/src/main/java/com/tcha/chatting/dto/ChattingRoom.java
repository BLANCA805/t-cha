package com.tcha.chatting.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.tcha.chatting.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

/*
    고려해야 하는 부분
    userProfileId, userName가 chattingRoom에 존재하는 것이 옳은가???
    옳지 않다면 이것은 어디로 전달되는 것이 맞는가?
    현재 채팅방에 존재하는 인원만 파악하면 되는 것인가??? => 그렇다면 int값으로 user와 trainer만 가지고 있으면 되는 것인지 확인해야 한다.
*/


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    //채팅방 Id
    private String id;
    private String name;

    //해당 채팅방을 구독하고 있는 유저의 userProfileId
//    private Long userProfileId;

    //해당 채팅방을 구독하고 있는 유저의 이름
//    private String userName;

    //해당 채팅방을 구독하고 있는 트레이너의 userProfile
//    private Long trainerUserProfileId;

    //해당 채팅방을 구독하고 있는 트레이너의 이름
//    private String trainerName;

    //???
//    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChattingRoom create(String name) {
        ChattingRoom chattingRoom = new ChattingRoom();
        chattingRoom.id = UUID.randomUUID().toString();
        chattingRoom.name = name;
        return chattingRoom;
    }

//    public void handleActions(WebSocketSession session, ChattingMessgae message,
//                              ChatService service) {
//        if (message.getType().equals(ChattingMessgae.MessageType.ENTER)) {
//            sessions.add(session);
//            message.setMessage(message.getSender() + "님이 입장하였습니다.");
//        }
//        sendMessage(message, service);
//    }
//
//    public <T> void sendMessage(T message, ChatService service) {
//        sessions.parallelStream().forEach(session -> service.sendMessage(message, session));
//    }
}
