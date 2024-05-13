package edu.spring2024.app.port;

import edu.spring2024.domain.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {

    Chat save(Chat chat);

    void delete(Chat chat);

    Optional<Chat> findById(Long id);

    List<Chat> findAll();

    List<Chat> findAllByUsersId(String userId);
}
