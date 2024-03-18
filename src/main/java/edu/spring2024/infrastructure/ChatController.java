package edu.spring2024.infrastructure;

import edu.spring2024.app.AuthService;
import edu.spring2024.app.MessageService;
import edu.spring2024.domain.ChatUser;
import edu.spring2024.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthService authService;

    @MessageMapping("/message")
    @SendTo("/topic")
    public void send(Message message) throws Exception {
        messageService.save(message);
    }

    @MessageMapping("/register")
    public void registerUser(ChatUser chatUser) throws Exception {
        authService.save(chatUser);
    }
}
