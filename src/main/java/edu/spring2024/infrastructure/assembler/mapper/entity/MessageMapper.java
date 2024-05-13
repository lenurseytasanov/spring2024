package edu.spring2024.infrastructure.assembler.mapper.entity;

import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.db.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final ModelMapper modelMapper;

    public MessageEntity toEntity(Message message) {
        return modelMapper.map(message, MessageEntity.class);
    }

    public Message toModel(MessageEntity messageEntity) {
        return modelMapper.map(messageEntity, Message.class);
    }
}
