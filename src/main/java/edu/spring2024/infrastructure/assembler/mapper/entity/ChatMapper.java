package edu.spring2024.infrastructure.assembler.mapper.entity;

import edu.spring2024.domain.Chat;
import edu.spring2024.infrastructure.db.entity.ChatEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final ModelMapper modelMapper;

    public ChatEntity toEntity(Chat chat) {
        return modelMapper.map(chat, ChatEntity.class);
    }

    public Chat toModel(ChatEntity chatEntity) {
        return modelMapper.map(chatEntity, Chat.class);
    }
}
