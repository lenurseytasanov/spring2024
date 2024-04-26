package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.ChatService;
import edu.spring2024.app.dto.chat.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/search-chat")
    public void searchChat(@Valid @Payload SearchChatRequest searchChatRequest) {

        messagingTemplate.convertAndSend(
                "/topic/%s".formatted(searchChatRequest.topic()),
                searchChatRequest
        );
    }

    @MessageMapping("/create-chat")
    public void createChat(@Valid @Payload CreateChatRequest createChatRequest) {

        ChatDto chatDto = chatService.createChat(
                createChatRequest.userFrom(), createChatRequest.userTo(), createChatRequest.topic());

        messagingTemplate.convertAndSendToUser(
                createChatRequest.userTo(), "/queue/reply",
                chatDto
        );
    }



    @DeleteMapping("/api/chat")
    public ResponseEntity<?> leaveChat(@Valid LeaveChatRequest leaveChatRequest) {
        ChatDto chatDto = chatService.leaveChat(leaveChatRequest.chatId(), leaveChatRequest.userId());
        return ResponseEntity.ok(chatDto);
    }


    @GetMapping("/api/chat")
    public ResponseEntity<?> getChat(@Valid GetChatRequest getChatRequest) {
        ChatDto chatDto = chatService.getChat(getChatRequest.chatId());
        return ResponseEntity.ok(chatDto);
    }
}
