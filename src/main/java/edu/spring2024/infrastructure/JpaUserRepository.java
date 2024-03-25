package edu.spring2024.infrastructure;

import edu.spring2024.app.UserRepository;
import edu.spring2024.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

    Optional<User> findByUsername(String username);
}
