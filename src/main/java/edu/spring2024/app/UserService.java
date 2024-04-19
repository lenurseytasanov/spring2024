package edu.spring2024.app;

import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для управления пользователями
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    /**
     * Сохраняет пользователя в базу данных
     * @param user новый пользователь
     * @return сохраненный пользователь
     */
    @Transactional
    public User save(User user) {
        User saved = userRepository.save(user);
        log.info("user {} saved", saved.getId());
        return saved;
    }

    /**
     * Удаляет пользователя из бд
     * @param id пользователь
     * @return удаленный пользователь
     */
    @Transactional
    public User deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        user.getChats().forEach(chat -> {
            chat.getUsers().remove(user);
            chatRepository.save(chat);
        });
        userRepository.delete(user);

        log.info("user {} deleted", user.getId());
        return user;
    }

    /**
     * Получает пользователя из бд
     * @param id пользователь
     * @return пользователь
     */
    @Transactional
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
