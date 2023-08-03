package com.tcha.chatting.service;

import com.tcha.chatting.dto.ChattingMessgae;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPubService {
    //redisTemplate: redis command를 수행
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(ChannelTopic topic, ChattingMessgae chattingMessgae) {
        redisTemplate.convertAndSend(topic.getTopic(), chattingMessgae);
    }
}
