package org.orekyuu.nozomi.presentation.websocket;

import org.orekyuu.nozomi.domain.project.ProjectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class NotificationWebSocketHandler extends AbstractWebSocketHandler {
    @EventListener
    public void onProjectEvent(ProjectEvent event) {
        broadcast(new WebSocketMessage<>(event.event(), Map.of("id", event.id().value())));
    }
}
