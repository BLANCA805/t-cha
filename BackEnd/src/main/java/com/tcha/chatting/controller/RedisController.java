package com.tcha.chatting.controller;

import com.tcha.chatting.dto.ChattingMessgae;
//import com.tcha.chatting.repository.ChattingRepository;
import com.tcha.chatting.repository.ChattingRepository;
import com.tcha.chatting.service.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RedisController {

    //메세지 발행을 위해 사용하는 publisher
    private final RedisPubService redisPublisher;
    private final ChattingRepository chattingRepository;

    @MessageMapping("/chatting/message")
    public void pubSub(@RequestBody ChattingMessgae chattingMessgae) {
        System.out.println("들어는 온단다");
        System.out.println(chattingMessgae.getContext() + "\n" + chattingMessgae.getSender() + "\n"
                + chattingMessgae.getType());
        //메세지 보내기
        redisPublisher.sendMessage(chattingRepository.getTopic(chattingMessgae.getRoomId()), chattingMessgae);
    }
}