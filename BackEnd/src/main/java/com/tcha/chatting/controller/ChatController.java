package com.tcha.chatting.controller;

import com.tcha.chatting.entity.Chat;
import com.tcha.chatting.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;

    @PostMapping
    public Chat createRoom(@RequestParam String name) {
        return service.createRoom(name);
    }

    @GetMapping
    public List<Chat> findAllRoom() {
        return service.findAllRoom();
    }
}
