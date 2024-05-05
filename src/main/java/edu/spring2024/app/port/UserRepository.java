package edu.spring2024.app.port;

import edu.spring2024.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    <S extends User> S save(S entity);

    void delete(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(String id);

    List<User> findAll();

    List<User> findAllByChatsId(Long chatId);
}
