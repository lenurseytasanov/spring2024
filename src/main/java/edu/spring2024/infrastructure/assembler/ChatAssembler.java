package edu.spring2024.infrastructure.assembler;

import edu.spring2024.domain.Chat;
import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.dto.ChatDto;
import edu.spring2024.infrastructure.dto.MessageDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Chat convertToEntity(ChatDto chatDto) {
        return modelMapper.map(chatDto, Chat.class);
    }

    public ChatDto convertToDto(Chat chat) {
        return modelMapper.map(chat, ChatDto.class);
    }
}
