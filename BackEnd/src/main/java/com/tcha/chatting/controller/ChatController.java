//package com.tcha.chatting.controller;
//
//import com.tcha.chatting.dto.Chat;
//import com.tcha.chatting.repository.ChatRepository;
//import com.tcha.chatting.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/chat")
//public class ChatController {
//
//    private final ChatRepository repository;
//
//    //채팅 리스트 화면
//    @GetMapping("/room")
//    public Chat createRoom(@RequestParam String name) {
//        return service.createRoom(name);
//    }
//
//    //모든 채팅방 목록 반환
//    @GetMapping
//    public List<Chat> findAllRoom() {
//        return service.findAllRoom();
//    }
//}
