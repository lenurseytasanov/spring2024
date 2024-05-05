package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.ChatService;
import edu.spring2024.app.dto.chat.ChatDto;
import edu.spring2024.app.dto.chat.CreateChatRequest;
import edu.spring2024.app.dto.chat.LeaveChatRequest;
import edu.spring2024.app.dto.chat.SearchChatRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT")
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
    @PreAuthorize("#leaveChatRequest.getUserId() == authentication.name")
    public ResponseEntity<ChatDto> leaveChat(@Valid LeaveChatRequest leaveChatRequest) {
        ChatDto chatDto = chatService.leaveChat(leaveChatRequest.chatId(), leaveChatRequest.userId());
        return ResponseEntity.ok(chatDto);
    }

    @GetMapping("/api/chat/{chatId}")
    @PostAuthorize("returnObject.getBody().getUserIds().contains(authentication.name)")
    public ResponseEntity<ChatDto> one(@NotNull @Min(1) @PathVariable Long chatId) {
        return ResponseEntity.ok(chatService.findById(chatId));
    }

    @GetMapping("/api/chat/all")
    public ResponseEntity<List<ChatDto>> all(@Min(1) @RequestParam(required = false) String userId) {
        return ResponseEntity.ok(chatService.findAll(userId));
    }
}
