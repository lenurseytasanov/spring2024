package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.infrastructure.db.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {

    List<ChatEntity> findAllByUsersId(String userId);
}
