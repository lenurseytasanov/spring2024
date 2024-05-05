package edu.spring2024.app.dto.chat;

import edu.spring2024.domain.ChatTopic;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    @NotNull @Min(1)
    private Long chatId;

    @NotNull @Size(min = 2, max = 2)
    private List<@NotNull @Size(min = 10, max = 50) String> userIds;

    @NotNull
    private ChatTopic topic;
}
