package edu.spring2024.services;

import edu.spring2024.Spring2024Application;
import edu.spring2024.app.UserService;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.ChatTopic;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.assembler.mapper.entity.ChatMapper;
import edu.spring2024.infrastructure.assembler.mapper.entity.UserMapper;
import edu.spring2024.infrastructure.configuration.ApplicationConfig;
import edu.spring2024.infrastructure.db.adapter.UserAdapter;
import edu.spring2024.infrastructure.db.entity.ChatEntity;
import edu.spring2024.infrastructure.db.entity.UserEntity;
import edu.spring2024.infrastructure.db.repository.JpaChatRepository;
import edu.spring2024.infrastructure.db.repository.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import({ UserService.class, ModelMapper.class, UserMapper.class, ChatMapper.class, UserAdapter.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = {Spring2024Application.class, ApplicationConfig.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChatMapper chatMapper;

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

        Chat chat = Chat.builder()
                .topic(ChatTopic.NO_TOPIC)
                .build();

        user1.getChats().add(chat);
        chat.getUsers().add(user1);

        UserEntity userEntity1 = userMapper.toEntity(user1);
        UserEntity userEntity2 = userMapper.toEntity(user2);
        ChatEntity chatEntity = chatMapper.toEntity(chat);

        jpaUserRepository.save(userEntity1);
        jpaUserRepository.save(userEntity2);
        jpaChatRepository.save(chatEntity);
    }


    @Test
    @Transactional
    void createUserTest() {
        // Arrange
        String userId = "3";
        String username = "username";

        // Act
        User user = userService.createUser(userId, username);

        // Assert
        assertEquals(userId, user.getId());
        assertEquals(username, user.getUsername());
        assertThrows(RuntimeException.class, () -> userService.createUser(userId1, username));
    }

    @Test
    @Transactional
    void deleteUserTest() {
        // Arrange
        String userId = "3";
        // Act
        User user = userService.deleteUser(userId1);

        // Assert
        assertEquals(userId1, user.getId());
        assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
    }

    @Test
    @Transactional
    void getUserTest() {
        // Arrange
        String userId = "3";
        // Act
        User user = userService.findBy(userId1);

        // Assert
        assertEquals(userId1, user.getId());
        assertThrows(RuntimeException.class, () -> userService.findBy(userId));
    }

    @Test
    @Transactional
    void gatAllUsersTest() {
        Long emptyChatId = 666L;
        List<String> expected = List.of(userId1, userId2);

        assertEquals(0, userService.findAll(emptyChatId).size());
        assertEquals(expected, userService.findAll(null).stream().map(User::getId).toList());
        assertEquals(expected, userService.findAll(null).stream().map(User::getId).toList());
    }
}
