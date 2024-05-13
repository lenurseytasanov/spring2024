package edu.spring2024.infrastructure.assembler.mapper.dto;

import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.controller.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {

    private final ModelMapper modelMapper;

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
