package edu.spring2024.services;

import edu.spring2024.app.UserService;
import edu.spring2024.app.dto.user.UserDto;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.db.repository.JpaChatRepository;
import edu.spring2024.infrastructure.db.repository.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import({ UserService.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    private static final String userId1 = "1";
    private static final String userId2 = "2";
    private static final Long chatId = 1L;


    @BeforeEach
    @Transactional
    void init() {
        User user1 = User.builder()
                .id(userId1)
                .username("user1")
                .build();
        User user2 = User.builder()
                .id(userId2)
                .username("user2")
                .build();

        Chat chat = new Chat();

        user1 = jpaUserRepository.save(user1);
        jpaUserRepository.save(user2);
        chat = jpaChatRepository.save(chat);

        user1.getChats().add(chat);
        chat.getUsers().add(user1);
    }


    @Test
    @Transactional
    void createUserTest() {
        // Arrange
        String userId = "3";
        String username = "username";

        // Act
        UserDto userDto = userService.createUser(userId, username);

        // Assert
        assertEquals(userId, userDto.getUserId());
        assertEquals(username, userDto.getUsername());
        assertThrows(RuntimeException.class, () -> userService.createUser(userId1, username));
    }

    @Test
    @Transactional
    void deleteUserTest() {
        // Arrange
        String userId = "3";
        // Act
        UserDto userDto = userService.deleteUser(userId1);

        // Assert
        assertEquals(userId1, userDto.getUserId());
        assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
    }

    @Test
    @Transactional
    void getUserTest() {
        // Arrange
        String userId = "3";
        // Act
        UserDto userDto = userService.findBy(userId1);

        // Assert
        assertEquals(userId1, userDto.getUserId());
        assertThrows(RuntimeException.class, () -> userService.findBy(userId));
    }

    @Test
    @Transactional
    void gatAllUsersTest() {
        Long emptyChatId = 666L;
        List<String> expected = List.of(userId1, userId2);

        assertEquals(0, userService.findAll(emptyChatId).size());
        assertEquals(expected, userService.findAll(null).stream().map(UserDto::getUserId).toList());
        assertEquals(expected, userService.findAll(null).stream().map(UserDto::getUserId).toList());
    }
}
