package org.orekyuu.nozomi.presentation.websocket;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebSocketTestUtil {

    public static CompletableFuture<String> connectAndWaitFirstMessage(WebSocketClient client, String uri) {
        return connectAndWaitFirstMessage(client, uri, null);
    }

    public static CompletableFuture<String> connectAndWaitFirstMessage(WebSocketClient client, String uri, Runnable onConnect) {
        return CompletableFuture.supplyAsync(() -> {
            CountDownLatch latch = new CountDownLatch(1);
            var handler = new TextWebSocketHandler() {
                String result;

                @Override
                protected void handleTextMessage(WebSocketSession session, TextMessage message) {
                    result = message.getPayload();
                    latch.countDown();
                }
            };

            ListenableFuture<WebSocketSession> future = client.doHandshake(handler, uri);
            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(Throwable ex) {
                    ex.printStackTrace();
                }

                @Override
                public void onSuccess(WebSocketSession result) {
                    onConnect.run();
                }
            });
            try {
                boolean await = latch.await(30, TimeUnit.SECONDS);
                if (!await) {
                    throw new RuntimeException("wait timeout");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return handler.result;
        });
    }
}
