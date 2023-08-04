package com.tcha.chatting.repository;
// import 생략....

import com.tcha.chatting.dto.ChattingRoom;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChattingRoomRepository {
    private Map<String, ChattingRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChattingRoom> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChattingRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public ChattingRoom createChatRoom(String name) {
        ChattingRoom chattingRoom = ChattingRoom.create(name);
        chatRoomMap.put(chattingRoom.getRoomId(), chattingRoom);
        return chattingRoom;
    }
}