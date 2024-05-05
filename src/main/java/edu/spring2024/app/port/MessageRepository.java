package edu.spring2024.app.port;

import edu.spring2024.domain.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    <S extends Message> S save(S entity);

    Optional<Message> findById(Long aLong);

    List<Message> findAllByChatId(Long chatId);

    List<Message> findAll();
}
