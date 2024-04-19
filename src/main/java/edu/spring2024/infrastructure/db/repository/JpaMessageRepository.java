package edu.spring2024.infrastructure.db.repository;

import edu.spring2024.app.port.MessageRepository;
import edu.spring2024.domain.Message;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMessageRepository extends CrudRepository<Message, Long>, MessageRepository {

    @Override
    @NonNull
    Message save(@NonNull Message entity);
}
