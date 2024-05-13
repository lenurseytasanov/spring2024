package edu.spring2024.infrastructure.db.adapter;

import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.assembler.mapper.entity.UserMapper;
import edu.spring2024.infrastructure.db.entity.UserEntity;
import edu.spring2024.infrastructure.db.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        return userMapper.toModel(jpaUserRepository.save(entity));
    }

    @Override
    public void delete(User user) {
        UserEntity entity = userMapper.toEntity(user);
        jpaUserRepository.delete(entity);
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<UserEntity> entity = jpaUserRepository.findById(id);
        return entity.map(userMapper::toModel);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = jpaUserRepository.findAll();
        return entities.stream()
                .map(userMapper::toModel)
                .toList();
    }

    @Override
    public List<User> findAllByChatsId(Long chatId) {
        List<UserEntity> entities = jpaUserRepository.findAllByChatsId(chatId);
        return entities.stream()
                .map(userMapper::toModel)
                .toList();
    }
}
