package com.tcha.chatting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcha.chatting.dto.ChattingMessgae;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubService implements MessageListener {

    //    public static List<String> messageList = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     * 메세지 전송 처리
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            //json으로 넘어온 데이터 파싱해서 chattingmessge클래스로 변환
            ChattingMessgae chattingMessgae = objectMapper.readValue(message.getBody(), ChattingMessgae.class);
            messagingTemplate.convertAndSend("/sub/chat/room/" + chattingMessgae.getRoomId(), chattingMessgae);
            System.out.println("받은 메세지 확인: " + message.toString());
            System.out.println("chatMessage.getSender() = " + chattingMessgae.getSender());
            System.out.println("chatMessage.getContext() = " + chattingMessgae.getContext());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}