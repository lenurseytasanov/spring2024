package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.domain.Chat;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long>, ChatRepository {

    @Override
    @NonNull
    Optional<Chat> findById(@NonNull Long aLong);

    @Override
    @NonNull
    <S extends Chat> S save(@NonNull S chat);

    @Override
    @Nonnull
    List<Chat> findAll();

    List<Chat> findAllByUsersId(String userId);
}
