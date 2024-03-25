package edu.spring2024.infrastructure;

import edu.spring2024.app.UserService;
import edu.spring2024.app.MessageService;
import edu.spring2024.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @MessageMapping("/message")
    @SendTo("/topic")
    public void send(Message message) throws Exception {
        messageService.save(message);
    }


}
