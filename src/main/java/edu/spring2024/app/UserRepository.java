package edu.spring2024.app;

import edu.spring2024.domain.ChatUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<ChatUser, Long> {
}
