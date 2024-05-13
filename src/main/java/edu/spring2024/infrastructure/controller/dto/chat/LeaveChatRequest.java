package edu.spring2024.infrastructure.controller.dto.chat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LeaveChatRequest(
        @NotNull @Size(min = 10, max = 50) String userId,
        @NotNull @Min(1) Long chatId) { }
