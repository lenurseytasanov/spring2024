package edu.spring2024.infrastructure;

import edu.spring2024.domain.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    public void send(Message message) throws Exception {
    }

}
