package edu.spring2024.infrastructure;

import edu.spring2024.app.ChatRepository;
import edu.spring2024.domain.Chat;
import org.springframework.data.repository.CrudRepository;

public interface JpaChatRepository extends CrudRepository<Chat, Long>, ChatRepository {
}
