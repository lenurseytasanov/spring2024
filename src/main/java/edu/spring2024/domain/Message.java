package edu.spring2024.domain;

import java.util.Date;

public record Message(
        String id,
        String chatId,
        String senderId,
        String recipientID,
        String content,
        MessageStatus status
) {
}
