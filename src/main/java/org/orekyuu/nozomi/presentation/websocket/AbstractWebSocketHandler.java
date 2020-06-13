package org.orekyuu.nozomi.presentation.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public abstract class AbstractWebSocketHandler extends TextWebSocketHandler {
    final WebSocketSessions sessions = new WebSocketSessions();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ObjectMapper mapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.removeSession(session);
    }

    public void broadcast(Object object) {
        try {
            String json = mapper.writeValueAsString(object);
            sessions.broadcast(new TextMessage(json));
        } catch (JsonProcessingException e) {
            logger.error("serialize error", e);
        }
    }
}
