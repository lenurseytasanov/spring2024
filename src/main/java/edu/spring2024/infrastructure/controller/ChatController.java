package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.ChatService;
import edu.spring2024.domain.Chat;
import edu.spring2024.infrastructure.dto.chat.ChatDto;
import edu.spring2024.infrastructure.dto.chat.ChatRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createChat(@Valid @RequestBody ChatRequest chatRequest) {
        try {
            Chat chat = chatService.createChat(chatRequest.getUserId());
            ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
            return ResponseEntity.ok(chatDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteChat(@Valid ChatDto chatRequest) {
        try {
            Chat chat = chatService.deleteChat(chatRequest.getId());
            ChatDto response = modelMapper.map(chat, ChatDto.class);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getChat(@Valid ChatDto chatRequest) {
        try {
            Chat chat = chatService.getChat(chatRequest.getId());
            ChatDto response = modelMapper.map(chat, ChatDto.class);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
