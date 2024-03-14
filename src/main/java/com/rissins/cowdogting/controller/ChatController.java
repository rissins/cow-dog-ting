package com.rissins.cowdogting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import static org.springframework.messaging.simp.stomp.StompHeaders.SESSION;

@Controller
public class ChatController {

    @MessageMapping("chat")
    @SendToUser("/queue/message")
    public String info(String message, SimpMessageHeaderAccessor messageHeaderAccessor) {
//        User talker = messageHeaderAccessor.getSessionId();
        SimpMessageType messageType = messageHeaderAccessor.getMessageType();
        return message;
    }

//    @MessageMapping("chat")
//    @SendTo("/topic/message")
//    public String chat(String message, SimpMessageHeaderAccessor messageHeaderAccessor) {
//        User talker = messageHeaderAccessor.getSessionAttributes().get(SESSION).get(USER_SESSION_KEY);
//        if(talker == null) throw new UnAuthenticationException("로그인한 사용자만 채팅에 참여할 수 있습니다.");
//        return message;
//    }

//    @MessageMapping("bye")
//    @SendTo("/topic/bye")
//    public User bye(String message) {
//        User talker = messageHeaderAccessor.getSessionAttributes().get(SESSION).get(USER_SESSION_KEY);
//        return talker;
//    }
}
