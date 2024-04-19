package edu.spring2024.infrastructure.dto.chat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatDto {

    @NotNull
    @Min(1)
    private Long id;
}
