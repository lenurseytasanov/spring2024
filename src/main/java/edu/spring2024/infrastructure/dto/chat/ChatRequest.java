package edu.spring2024.infrastructure.dto.chat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChatRequest {

    @NotNull
    @Size(min = 4, max = 50)
    private String userId;
}
