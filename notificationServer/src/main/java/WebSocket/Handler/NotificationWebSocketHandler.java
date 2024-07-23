package WebSocket.Handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class NotificationWebSocketHandler extends TextWebSocketHandler {
    //밑에꺼 자동완성이라 뭐 어케써야하는거냐? 하나도 모르겠네
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, IOException {
        String payload = message.getPayload();
        // Handle the received message
        System.out.println("Received: " + payload);

        // Echo the received message back to the client
        session.sendMessage(new TextMessage("Echo: " + payload));
    }
}
