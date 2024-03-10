package edu.spring2024.app;

import edu.spring2024.domain.Message;

public interface MessageRepository {

    Message save(Message message);
}
