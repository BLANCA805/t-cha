//package com.tcha.chatting.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tcha.chatting.dto.Chat;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.io.IOException;
//import java.util.*;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class ChatService {
//
//    private final ObjectMapper objectMapper;
//    private Map<String, Chat> chatRooms;
//
//    @PostConstruct
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }
//
//    public List<Chat> findAllRoom() {
//        return new ArrayList<>(chatRooms.values());
//    }
//
//    public Chat findRoomById(String roomId) {
//        return chatRooms.get(roomId);
//    }
//
//    public Chat createRoom(String name) {
//        String randomId = UUID.randomUUID().toString();
//        Chat chatRoom = Chat.builder()
//                .id(randomId)
//                .name(name)
//                .build();
//        chatRooms.put(randomId, chatRoom);
//        return chatRoom;
//    }
//
//    public <T> void sendMessage(T message, WebSocketSession session) {
//        try {
//            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//}