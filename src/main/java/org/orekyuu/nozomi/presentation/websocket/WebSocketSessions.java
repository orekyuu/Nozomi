package org.orekyuu.nozomi.presentation.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebSocketSessions {
    private final List<WebSocketSession> sessionList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketSessions.class);

    public void addSession(WebSocketSession session) {
        synchronized (sessionList) {
            if (sessionList.stream().noneMatch(it -> Objects.equals(it.getId(), session.getId()))) {
                sessionList.add(session);
            }
        }
    }

    public void removeSession(WebSocketSession session) {
        synchronized (sessionList) {
            sessionList.removeIf(it -> Objects.equals(it.getId(), session.getId()));
        }
    }

    public int sessionCount() {
        return sessionList.size();
    }

    public void broadcast(TextMessage textMessage) {
        synchronized (sessionList) {
            sessionList.forEach(session -> {
                try {
                    session.sendMessage(textMessage);
                } catch (IOException e) {
                    logger.warn("error on sendMessage", e);
                }
            });
        }
    }
}
