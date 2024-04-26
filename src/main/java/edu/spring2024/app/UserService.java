package edu.spring2024.app;

import edu.spring2024.app.dto.user.UserDto;
import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
        if (userRepository.findById(userId).isPresent()) {
            log.debug("user {} already saved", userId);
            throw new IllegalArgumentException();
        }

        User user = new User(userId, username);
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
        User user = userRepository.findById(userId).orElseThrow();

        user.getChats().forEach(chat -> {
            chat.getUsers().remove(user);
            chatRepository.save(chat);
        });

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
    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return convertUserToDto(user);
    }
}
