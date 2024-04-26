package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, String>, UserRepository {

    @Override
    @NonNull
    User save(@NonNull User entity);

    Optional<User> findByUsername(String username);
}
