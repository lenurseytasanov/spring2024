package edu.spring2024.infrastructure.assembler.mapper.dto;

import edu.spring2024.domain.Chat;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.controller.dto.chat.ChatDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ChatDtoMapper {

    private final ModelMapper modelMapper;

    public ChatDto toDto(Chat chat) {
        modelMapper
                .typeMap(Chat.class, ChatDto.class)
                .addMappings(mapper -> mapper
                        .using(ctx -> ((Set<?>) ctx.getSource()).stream()
                                .map(User.class::cast)
                                .map(User::getId)
                                .toList())
                        .map(Chat::getUsers, ChatDto::setUserIds))
                .addMapping(Chat::getId, ChatDto::setChatId);
        return modelMapper.map(chat, ChatDto.class);
    }

    public static void solve() {
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0)
                System.out.print("Fizz");
            if (i % 5 == 0)
                System.out.print("Buzz");
            if (i % 3 != 0 && i % 5 != 0)
                System.out.print(i);
            System.out.println();
        }
    }
}
