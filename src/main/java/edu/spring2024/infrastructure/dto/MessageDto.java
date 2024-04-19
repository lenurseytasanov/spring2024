package edu.spring2024.infrastructure.dto;

import lombok.Data;

@Data
public class MessageDto {

    private String content;

    private String senderId;

    private String recipientId;

    private Long chatId;
}
