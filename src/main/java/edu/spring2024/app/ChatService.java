package edu.spring2024.app;

import edu.spring2024.domain.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat newChat() {
        Chat chat = chatRepository.save(new Chat());
        log.info("chat {} created", chat.getId());
        return chat;
    }
}
