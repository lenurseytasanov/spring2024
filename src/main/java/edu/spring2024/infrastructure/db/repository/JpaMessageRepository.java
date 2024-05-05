package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.app.port.MessageRepository;
import edu.spring2024.domain.Message;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaMessageRepository extends JpaRepository<Message, Long>, MessageRepository {

    @Override
    @Nonnull
    <S extends Message> S save(@Nonnull S entity);

    @Override
    @Nonnull
    Optional<Message> findById(@Nonnull Long aLong);

    List<Message> findAllByChatId(Long chatId);

    @Override
    @Nonnull
    List<Message> findAll();
}
