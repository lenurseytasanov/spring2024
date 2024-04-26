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
    void init() {
        User user1 = new User(userId1, "user1");
        User user2 = new User(userId2, "user2");

        Chat chat = Chat.builder()
                .id(chatId)
                .build();

        user1 = jpaUserRepository.save(user1);
        jpaUserRepository.save(user2);
        chat = jpaChatRepository.save(chat);

        user1.getChats().add(chat);
        chat.getUsers().add(user1);
    }


    @Test
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
    void getUserTest() {
        // Arrange
        String userId = "3";
        // Act
        UserDto userDto = userService.getUser(userId1);

        // Assert
        assertEquals(userId1, userDto.getUserId());
        assertThrows(RuntimeException.class, () -> userService.getUser(userId));
    }
}
