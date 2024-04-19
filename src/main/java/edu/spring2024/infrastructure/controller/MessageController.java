package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.MessageService;
import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.dto.MessageDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final ModelMapper modelMapper;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void send(@Payload MessageDto messageDto) {
        Message message = new Message(messageDto.getContent());

        try {
            message = messageService.save(message, messageDto.getChatId(),
                    messageDto.getSenderId(), messageDto.getRecipientId());
        } catch (EntityNotFoundException e) {
            return;
        }

        MessageDto processedMessage = modelMapper.map(message, MessageDto.class);
        messagingTemplate.convertAndSendToUser(
                processedMessage.getRecipientId(), "/queue/reply", processedMessage);
    }
}
