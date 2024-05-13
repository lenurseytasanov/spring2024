package edu.spring2024.infrastructure.controller.dto.message;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    @Min(1)
    private Long id;

    @NotNull @Size(min = 1, max = 300)
    private String content;

    @NotNull @Size(min = 10, max = 50)
    private String senderId;

    @NotNull @Size(min = 10, max = 50)
    private String recipientId;

    @NotNull @Min(1)
    private Long chatId;
}
