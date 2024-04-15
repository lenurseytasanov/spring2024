package edu.spring2024.infrastructure;

import edu.spring2024.app.ChatService;
import edu.spring2024.app.UserService;
import edu.spring2024.app.MessageService;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.assembler.ChatAssembler;
import edu.spring2024.infrastructure.assembler.MessageAssembler;
import edu.spring2024.infrastructure.dto.ChatDto;
import edu.spring2024.infrastructure.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageAssembler messageAssembler;

    @Autowired
    private ChatAssembler chatAssembler;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/{chat_id}")
    public void send(MessageDto messageDto) throws Exception {
        Message message = messageAssembler.convertToEntity(messageDto);
        messageService.save(message);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatDto> createChat() {
        Chat chat = chatService.newChat();
        return ResponseEntity.ok(chatAssembler.convertToDto(chat));
    }
}
