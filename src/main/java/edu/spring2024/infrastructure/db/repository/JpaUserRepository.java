package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.infrastructure.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findAllByChatsId(Long chatId);
}
