package edu.spring2024.app.dto.chat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GetChatRequest(
        @NotNull @Min(1) Long chatId) { }
