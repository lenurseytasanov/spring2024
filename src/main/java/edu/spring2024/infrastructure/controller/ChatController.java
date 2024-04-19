package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.ChatService;
import edu.spring2024.domain.Chat;
import edu.spring2024.infrastructure.dto.chat.ChatDto;
import edu.spring2024.infrastructure.dto.chat.ChatRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody ChatRequest chatRequest) {
        try {
            Chat chat = chatService.createChat(chatRequest.getUserId());
            ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
            return ResponseEntity.ok(chatDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteChat(Long chatId) {
        try {
            Chat chat = chatService.deleteChat(chatId);
            ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
            return ResponseEntity.ok(chatDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getChat(Long chatId) {
        try {
            Chat chat = chatService.getChat(chatId);
            ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
            return ResponseEntity.ok(chatDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
