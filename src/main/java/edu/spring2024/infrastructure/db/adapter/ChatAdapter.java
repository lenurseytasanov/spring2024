package edu.spring2024.infrastructure.db.adapter;

import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.domain.Chat;
import edu.spring2024.infrastructure.assembler.mapper.entity.ChatMapper;
import edu.spring2024.infrastructure.db.entity.ChatEntity;
import edu.spring2024.infrastructure.db.repository.JpaChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatAdapter implements ChatRepository {

    private final JpaChatRepository jpaChatRepository;

    private final ChatMapper chatMapper;

    @Override
    public Chat save(Chat chat) {
        ChatEntity entity = chatMapper.toEntity(chat);
        return chatMapper.toModel(jpaChatRepository.save(entity));
    }

    @Override
    public void delete(Chat chat) {
        ChatEntity entity = chatMapper.toEntity(chat);
        jpaChatRepository.delete(entity);
    }

    @Override
    public Optional<Chat> findById(Long id) {
        Optional<ChatEntity> entity = jpaChatRepository.findById(id);
        return entity.map(chatMapper::toModel);
    }

    @Override
    public List<Chat> findAll() {
        List<ChatEntity> entities = jpaChatRepository.findAll();
        return entities.stream()
                .map(chatMapper::toModel)
                .toList();
    }

    @Override
    public List<Chat> findAllByUsersId(String userId) {
        List<ChatEntity> entities = jpaChatRepository.findAllByUsersId(userId);
        return entities.stream()
                .map(chatMapper::toModel)
                .toList();
    }
}
