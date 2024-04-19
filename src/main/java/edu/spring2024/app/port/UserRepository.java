package edu.spring2024.app.port;

import edu.spring2024.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void delete(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(String id);
}
