package edu.spring2024.infrastructure.assembler.mapper.dto;

import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.controller.dto.message.MessageDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDtoMapper {

    private final ModelMapper modelMapper;

    public MessageDto toDto(Message message) {
        return modelMapper.map(message, MessageDto.class);
    }
}
