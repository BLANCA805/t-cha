//package com.tcha.chatting.dto;
//
////import com.tcha.chatting.service.ChatService;
////import lombok.Builder;
//
//import com.tcha.chatting.service.ChatService;
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
////import org.springframework.web.socket.WebSocketSession;
////
////import java.util.HashSet;
////import java.util.Set;
//import java.util.UUID;
//import org.springframework.web.socket.WebSocketSession;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Channel implements Serializable {
//
//    private static final long serialVersionUID = 6494678977089006639L;
//
//    private String id;
//    private String name;
//    private Set<WebSocketSession> sessions = new HashSet<>();
//
////    @Builder
////    public Channel(String id, String name) {
////        this.id = id;
////        this.name = name;
////    }
//
//    public static Channel create(String name) {
//        Channel channel = new Channel();
//        channel.id = UUID.randomUUID().toString();
//        channel.name = name;
//        return channel;
//    }
//
//    public void handleActions(WebSocketSession session, ChannelMessage message,
//            ChatService service) {
//        if (message.getType().equals(ChannelMessage.MessageType.ENTER)) {
//            sessions.add(session);
//            message.setMessage(message.getSender() + "님이 입장하였습니다.");
//        }
//        sendMessage(message, service);
//    }
//
//    public <T> void sendMessage(T message, ChatService service) {
//        sessions.parallelStream().forEach(session -> service.sendMessage(message, session));
//    }
//}
