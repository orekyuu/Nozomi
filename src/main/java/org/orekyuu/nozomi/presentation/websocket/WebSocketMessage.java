package org.orekyuu.nozomi.presentation.websocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.orekyuu.nozomi.domain.basic.event.EventType;

public class WebSocketMessage<T> {
    final EventType type;
    final T data;

    @JsonCreator
    public WebSocketMessage(EventType type, T data) {
        this.type = type;
        this.data = data;
    }
}
