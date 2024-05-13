package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.infrastructure.db.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByChatId(Long chatId);
}
