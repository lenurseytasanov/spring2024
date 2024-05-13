package edu.spring2024.app;

import edu.spring2024.app.exception.UserNotFoundException;
import edu.spring2024.app.exception.UserNotUniqueException;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для управления пользователями
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Создает нового пользователя
     * @param userId индентификатор пользователя
     * @param username имя пользователя
     * @return пользователь
     */
    @Transactional
    public User createUser(String userId, String username) {

        userRepository.findById(userId).ifPresent(user -> {
            throw new UserNotUniqueException();
        });

        User user = User.builder()
                .id(userId)
                .username(username)
                .build();

        user = userRepository.save(user);
        log.info("user {} saved", userId);
        return user;
    }

    /**
     * Удаляет пользователя из бд
     * @param userId индентификатор пользователя
     * @return удаленный пользователь
     */
    @Transactional
    public User deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);

        log.info("user {} deleted", user.getId());
        return user;
    }

    /**
     * Возварщает пользователя из бд
     * @param userId индентификатор пользователя
     * @return пользователь
     */
    @Transactional
    public User findBy(String userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Возвращает список пользователей
     * @param chatId чат
     * @return участники чата
     */
    @Transactional
    public List<User> findAll(Long chatId) {
        if (chatId == null) {
            return userRepository.findAll();
        }

        return userRepository.findAllByChatsId(chatId);
    }
}
