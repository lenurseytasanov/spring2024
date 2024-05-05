package edu.spring2024.app.dto.message;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageDto(

        @Min(1) Long id,
        @NotNull @Size(min = 1, max = 300) String content,
        @NotNull @Size(min = 10, max = 50) String senderId,
        @NotNull @Size(min = 10, max = 50) String recipientId,
        @NotNull @Min(1) Long chatId) { }
