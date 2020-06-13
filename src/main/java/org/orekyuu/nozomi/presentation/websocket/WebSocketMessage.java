package org.orekyuu.nozomi.presentation.websocket;

import com.fasterxml.jackson.annotation.JsonCreator;

public class WebSocketMessage<T> {
    enum Type {
        PROJECT_CREATED
    }

    final Type type;
    final T data;

    @JsonCreator
    public WebSocketMessage(Type type, T data) {
        this.type = type;
        this.data = data;
    }
}
