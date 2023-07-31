//package com.tcha.chatting.service;
//
//import com.tcha.chatting.dto.ChannelMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class RedisPublisher {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public void publish(ChannelTopic topic, ChannelMessage message) {
//        redisTemplate.convertAndSend(topic.getTopic(), message);
//    }
//}
