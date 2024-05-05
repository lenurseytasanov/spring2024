package edu.spring2024.app;

import edu.spring2024.app.dto.user.UserDto;
import edu.spring2024.app.exception.UserNotFoundException;
import edu.spring2024.app.exception.UserNotUniqueException;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    private UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Создает нового пользователя
     * @param userId индентификатор пользователя
     * @param username имя пользователя
     * @return пользователь
     */
    @Transactional
    public UserDto createUser(String userId, String username) {

        userRepository.findById(userId).ifPresent(user -> {
            throw new UserNotUniqueException();
        });

        User user = User.builder()
                .id(userId)
                .username(username)
                .build();

        user = userRepository.save(user);
        log.info("user {} saved", userId);
        return convertUserToDto(user);
    }

    /**
     * Удаляет пользователя из бд
     * @param userId индентификатор пользователя
     * @return удаленный пользователь
     */
    @Transactional
    public UserDto deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.getChats().forEach(chat -> chat.getUsers().remove(user));

        userRepository.delete(user);

        log.info("user {} deleted", user.getId());
        return convertUserToDto(user);
    }

    /**
     * Возварщает пользователя из бд
     * @param userId индентификатор пользователя
     * @return пользователь
     */
    @Transactional
    public UserDto findBy(String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return convertUserToDto(user);
    }

    /**
     * Возвращает список пользователей
     * @param chatId чат
     * @return участники чата
     */
    @Transactional
    public List<UserDto> findAll(Long chatId) {
        if (chatId == null) {
            return userRepository.findAll().stream()
                    .map(this::convertUserToDto)
                    .toList();
        }

        return userRepository.findAllByChatsId(chatId).stream()
                .map(this::convertUserToDto)
                .toList();
    }
}
