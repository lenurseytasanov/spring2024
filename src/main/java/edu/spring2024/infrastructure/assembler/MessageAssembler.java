package edu.spring2024.infrastructure.assembler;

import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.dto.MessageDto;
import org.hibernate.boot.jaxb.hbm.internal.RepresentationModeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Message convertToEntity(MessageDto messageDto) {
        return modelMapper.map(messageDto, Message.class);
    }

    public MessageDto convertToDto(Message message) {
        return modelMapper.map(message, MessageDto.class);
    }
}
