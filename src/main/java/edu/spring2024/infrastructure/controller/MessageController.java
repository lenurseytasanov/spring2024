package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.MessageService;
import edu.spring2024.app.dto.message.MessageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void send(@Valid @Payload MessageDto messageDto) {
        MessageDto processedMessage = messageService.save(messageDto.chatId(),
                messageDto.senderId(), messageDto.recipientId(), messageDto.content());
        messagingTemplate.convertAndSendToUser(
                processedMessage.recipientId(), "/queue/reply", processedMessage);
    }
}
