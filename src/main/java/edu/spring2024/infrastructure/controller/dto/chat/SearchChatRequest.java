package edu.spring2024.infrastructure.controller.dto.chat;

import edu.spring2024.domain.ChatTopic;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SearchChatRequest(
        @NotNull @Size(min = 10, max = 50) String userFrom,
        @NotNull ChatTopic topic) { }
