//package com.tcha.chatting.controller;
//
//import com.tcha.chatting.dto.ChannelMessage;
//import com.tcha.chatting.repository.ChattingRepository;
//import com.tcha.chatting.service.RedisPublisher;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.web.bind.annotation.*;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/chat")
//public class ChattingController {
//
//
//    private final RedisPublisher redisPublisher;
//    private final ChattingRepository chattingRepository;
//
//    @MessageMapping("/chat/message")
//    public void message(ChannelMessage message) {
//        if (ChannelMessage.MessageType.ENTER.equals(message.getType())) {
//            chattingRepository.enterChatRoom(String.valueOf(message.getChannelId()));
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        }
//        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
//        redisPublisher.publish(chattingRepository.getTopic(String.valueOf(message.getChannelId())),
//                message);
//    }
//}