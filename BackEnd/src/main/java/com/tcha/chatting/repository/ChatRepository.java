//package com.tcha.chatting.repository;
//
//import com.tcha.chatting.dto.Chat;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//// import 생략....
//
//@Repository
//public class ChatRepository {
//
//    private Map<String, Chat> chatRoomMap;
//
//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }
//
//    public List<Chat> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
//
//    public Chat findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }
//
//    public Chat createChatRoom(String name) {
//        Chat chatRoom = Chat.create(name);
//        chatRoomMap.put(chatRoom.getId(), chatRoom);
//        return chatRoom;
//    }
//}