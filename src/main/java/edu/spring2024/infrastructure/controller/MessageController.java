package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.MessageService;
import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.assembler.mapper.dto.MessageDtoMapper;
import edu.spring2024.infrastructure.controller.dto.message.MessageDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT")

@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageDtoMapper messageDtoMapper;

    @MessageMapping("/message")
    public void send(@Valid @Payload MessageDto messageDto) {
        Message processedMessage = messageService.save(messageDto.getChatId(),
                messageDto.getSenderId(), messageDto.getRecipientId(), messageDto.getContent());
        MessageDto messageDto1 = messageDtoMapper.toDto(processedMessage);
        messagingTemplate.convertAndSendToUser(
                messageDto1.getRecipientId(), "/queue/reply", messageDto1);
    }

    @GetMapping("/api/message/{id}")
    public ResponseEntity<MessageDto> one(@NotNull @Min(1) @PathVariable Long id) {
        return ResponseEntity.ok(messageDtoMapper.toDto(messageService.findById(id)));
    }

    @GetMapping("/api/message/all")
    public ResponseEntity<List<MessageDto>> all(@NotNull @Min(1) @RequestParam(required = false) Long chatId) {
        return ResponseEntity.ok(messageService.findAll(chatId).stream()
                .map(messageDtoMapper::toDto)
                .toList());
    }
}
