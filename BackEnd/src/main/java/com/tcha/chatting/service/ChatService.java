//package com.tcha.chatting.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tcha.chatting.dto.Channel;
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
//    private Map<String, Channel> chatRooms;
//
//    @PostConstruct
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }
//
//    public List<Channel> findAllRoom() {
//        return new ArrayList<>(chatRooms.values());
//    }
//
//    public Channel findRoomById(String roomId) {
//        return chatRooms.get(roomId);
//    }
//
//    public Channel createRoom(String name) {
//        String randomId = UUID.randomUUID().toString();
//        Channel chatRoom = Channel.builder()
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