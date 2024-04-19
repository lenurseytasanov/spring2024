package edu.spring2024.app.port;

import edu.spring2024.domain.Message;

public interface MessageRepository {

    Message save(Message message);
}
