package edu.spring2024.app;

import edu.spring2024.domain.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    /**
     * Сохраняет пользователя в базу данных
     * @param chatUser новый пользователь
     * @return сохраненный пользователь с новым id
     */
    public ChatUser save(ChatUser chatUser) {
        ChatUser saved = userRepository.save(chatUser);
        LOGGER.info("user {} saved", saved.getId());
        return saved;
    }
}
