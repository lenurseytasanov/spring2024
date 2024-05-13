package edu.spring2024.infrastructure.db.adapter;

import edu.spring2024.app.port.MessageRepository;
import edu.spring2024.domain.Message;
import edu.spring2024.infrastructure.assembler.mapper.entity.MessageMapper;
import edu.spring2024.infrastructure.db.entity.MessageEntity;
import edu.spring2024.infrastructure.db.repository.JpaMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {

    private final JpaMessageRepository jpaMessageRepository;

    private final MessageMapper messageMapper;

    @Override
    public Message save(Message message) {
        MessageEntity entity = messageMapper.toEntity(message);
        return messageMapper.toModel(jpaMessageRepository.save(entity));
    }

    @Override
    public Optional<Message> findById(Long id) {
        Optional<MessageEntity> entity = jpaMessageRepository.findById(id);
        return entity.map(messageMapper::toModel);
    }

    @Override
    public List<Message> findAllByChatId(Long chatId) {
        List<MessageEntity> entities = jpaMessageRepository.findAllByChatId(chatId);
        return entities.stream()
                .map(messageMapper::toModel)
                .toList();
    }

    @Override
    public List<Message> findAll() {
        List<MessageEntity> entities = jpaMessageRepository.findAll();
        return entities.stream()
                .map(messageMapper::toModel)
                .toList();
    }
}
