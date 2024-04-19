package edu.spring2024.infrastructure.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageDto {

    @NotNull
    @Size(min = 1, max = 300)
    private String content;

    @NotNull
    @Size(min = 4, max = 40)
    private String senderId;

    @NotNull
    @Size(min = 4, max = 40)
    private String recipientId;

    @NotNull
    @Min(1)
    private Long chatId;
}
