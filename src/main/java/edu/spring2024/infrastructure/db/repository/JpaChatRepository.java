package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.domain.Chat;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long>, ChatRepository {

    @Override
    @NonNull
    Optional<Chat> findById(@NonNull Long aLong);

    @Override
    @NonNull
    Chat save(@NonNull Chat chat);
}
