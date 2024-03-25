package edu.spring2024.infrastructure;

import edu.spring2024.app.MessageRepository;
import edu.spring2024.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface JpaMessageRepository extends CrudRepository<Message, Long>, MessageRepository {
}
