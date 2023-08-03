package com.tcha.chatting.controller;

import com.tcha.chatting.dto.ChattingRoom;
import com.tcha.chatting.repository.ChattingRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatting-rooms")
public class ChannelController {

    private final ChattingRepository chatRoomRepository;


    @GetMapping("/rooms")
    @ResponseBody
    public List<ChattingRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChattingRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId", roomId);
//        return "/chat/roomdetail";
//    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChattingRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
