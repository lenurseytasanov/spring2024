package edu.spring2024.services;

import edu.spring2024.Spring2024Application;
import edu.spring2024.app.ChatService;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.ChatTopic;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.assembler.mapper.entity.ChatMapper;
import edu.spring2024.infrastructure.assembler.mapper.entity.UserMapper;
import edu.spring2024.infrastructure.configuration.ApplicationConfig;
import edu.spring2024.infrastructure.db.adapter.ChatAdapter;
import edu.spring2024.infrastructure.db.adapter.UserAdapter;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ ChatService.class, ModelMapper.class, UserMapper.class, ChatMapper.class, ChatAdapter.class, UserAdapter.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = {Spring2024Application.class, ApplicationConfig.class})
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

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

        jpaUserRepository.save(userMapper.toEntity(user1));
        jpaUserRepository.save(userMapper.toEntity(user2));
        jpaChatRepository.save(chatMapper.toEntity(chat));
    }

    @Test
    @Transactional
    void createChatTest() {
        // Arrange
        String userId = "666";
        ChatTopic topic = ChatTopic.NO_TOPIC;

        // Act
        Chat chat = chatService.createChat(userId1, userId2, topic);

        // Assert
        assertTrue(chat.getUsers().stream().anyMatch(user -> user.getId().equals(userId1)));
        assertTrue(chat.getUsers().stream().anyMatch(user -> user.getId().equals(userId2)));
        assertEquals(topic, chat.getTopic());
        assertThrows(RuntimeException.class, () -> chatService.createChat(userId1, userId, topic));
    }

    @Test
    @Transactional
    void leaveChatTest() {
        // Arrange
        String userId = "666";

        // Act
        Chat chat = chatService.leaveChat(chatId, userId1);

        // Assert
        assertThrows(RuntimeException.class, () -> chatService.leaveChat(chatId, userId));
        assertEquals(chatId, chat.getId());
        assertTrue(chat.getUsers().isEmpty());
    }

    @Test
    @Transactional
    void leaveChatTestNotParticipantTest() {
        // Arrange

        // Act
        Chat chat = chatService.leaveChat(chatId, userId2);

        // Assert
        assertEquals(chatId, chat.getId());
        assertTrue(chat.getUsers().stream().anyMatch(user -> user.getId().equals(userId1)));
    }

    @Test
    @Transactional
    void getChatTest() {
        // Arrange
        Long emptyChatId = 666L;

        // Act
        Chat chat = chatService.findById(chatId);

        // Assert
        assertThrows(RuntimeException.class, () -> chatService.findById(emptyChatId));
        assertEquals(chatId, chat.getId());
    }

    @Test
    @Transactional
    void gatAllChatsTest() {
        String emptyUserId = "-1";
        assertEquals(0, chatService.findAll(emptyUserId).size());
        assertEquals(chatId, chatService.findAll(null).getFirst().getId());
        assertEquals(chatId, chatService.findAll(userId1).getFirst().getId());
    }
}
