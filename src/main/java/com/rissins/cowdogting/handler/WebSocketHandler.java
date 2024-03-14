package com.rissins.cowdogting.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessionMap.put(sessionId, session);

        Message message = Message.builder()
                .sender(sessionId)
                .receiver("all")
                .build();

        message.newConnect();

        sessionMap.values().forEach(s -> {
            try {
                if (!s.getId().equals(sessionId)) {
                    s.sendMessage(new TextMessage("connect Session Id >> " + sessionId));
                }
            } catch (Exception e) {
                log.error("WebSocket Connect Error : Session Id = {}",sessionId);
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        Message payloadMessage = mapper.readValue(payload, Message.class);

        WebSocketSession receiver = sessionMap.get(payloadMessage.getReceiver());

        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(message.toString()));
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessionMap.values().forEach(s -> {
            try {
                if (!s.getId().equals(sessionId)) {
                    s.sendMessage(new TextMessage("Disconnect Session Id >> " + sessionId));
                }
            } catch (Exception e) {
                log.error("WebSocket Disconnect Error : Session Id = {}",sessionId);
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }
}
