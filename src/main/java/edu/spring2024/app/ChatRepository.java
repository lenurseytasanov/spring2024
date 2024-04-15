package edu.spring2024.app;

import edu.spring2024.domain.Chat;

public interface ChatRepository {
    Chat save(Chat chat);
}
