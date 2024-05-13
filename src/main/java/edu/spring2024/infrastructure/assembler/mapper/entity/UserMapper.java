package edu.spring2024.infrastructure.assembler.mapper.entity;

import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.db.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserEntity toEntity(User user) {
        return modelMapper.map(user, UserEntity.class);
    }

    public User toModel(UserEntity userEntity) {
        return modelMapper.map(userEntity, User.class);
    }
}
