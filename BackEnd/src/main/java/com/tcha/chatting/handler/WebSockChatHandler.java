//package com.tcha.chatting.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tcha.chatting.dto.Channel;
//import com.tcha.chatting.dto.ChannelMessage;
//import com.tcha.chatting.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class WebSockChatHandler extends TextWebSocketHandler {
//
//    private final ObjectMapper objectMapper;
//
//    private final ChatService service;
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message)
//            throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
////        TextMessage textMessage = new TextMessage("testing");
////        session.sendMessage(textMessage);
//
//        ChannelMessage chatMessage = objectMapper.readValue(payload, ChannelMessage.class);
//        Channel room = service.findRoomById(String.valueOf(chatMessage.getChannelId()));
//        room.handleActions(session, chatMessage, service);
//
//    }
//}
