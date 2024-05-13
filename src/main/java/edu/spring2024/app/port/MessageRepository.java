package edu.spring2024.app.port;

import edu.spring2024.domain.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Message save(Message message);

    Optional<Message> findById(Long id);

    List<Message> findAllByChatId(Long chatId);

    List<Message> findAll();
}
