package edu.spring2024.app;

import edu.spring2024.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);
}
