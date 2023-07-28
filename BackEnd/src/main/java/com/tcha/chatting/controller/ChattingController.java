package com.tcha.chatting.controller;

import com.tcha.chatting.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChattingController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/hello") //클라이언트에서, /pub/hello로 메세지 발행
    public void message(Message message) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(),
                message);
    }
}
