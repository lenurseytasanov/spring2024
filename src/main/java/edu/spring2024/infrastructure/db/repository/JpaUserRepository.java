package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, String>, UserRepository {

    @Override
    @Nonnull
    <S extends User> S save(@Nonnull S entity);

    Optional<User> findByUsername(String username);

    @Override
    @Nonnull
    List<User> findAll();

    List<User> findAllByChatsId(Long chatId);
}
