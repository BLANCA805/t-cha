package com.tcha.chatting.controller;

import com.tcha.chatting.dto.ChattingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChattingController {

    private final SimpMessageSendingOperations SimpMessageSendingOperations;

    @MessageMapping("/hello")
    public void message(ChattingMessage chattingMessage) {
        System.out.println("testing");
        SimpMessageSendingOperations.convertAndSend("/sub/channel/" + chattingMessage.getRoomId(), chattingMessage);


//        if (ChattingMessage.MessageType.JOIN.equals(message.getType()))
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}