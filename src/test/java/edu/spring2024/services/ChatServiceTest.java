package edu.spring2024.services;

import edu.spring2024.app.ChatService;
import edu.spring2024.app.dto.chat.ChatDto;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.ChatTopic;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ ChatService.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

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
    void createChatTest() {
        // Arrange
        String userId = "666";
        ChatTopic topic = ChatTopic.NO_TOPIC;

        // Act
        ChatDto chatDto = chatService.createChat(userId1, userId2, topic);

        // Assert
        assertTrue(chatDto.getUserIds().contains(userId1));
        assertTrue(chatDto.getUserIds().contains(userId2));
        assertEquals(topic, chatDto.getTopic());
        assertThrows(RuntimeException.class, () -> chatService.createChat(userId1, userId, topic));
    }

    @Test
    @Transactional
    void leaveChatTest() {
        // Arrange
        String userId = "666";

        // Act
        ChatDto chatDto = chatService.leaveChat(chatId, userId1);

        // Assert
        assertThrows(RuntimeException.class, () -> chatService.leaveChat(chatId, userId));
        assertEquals(chatId, chatDto.getChatId());
        assertTrue(chatDto.getUserIds().isEmpty());
    }

    @Test
    @Transactional
    void leaveChatTestNotParticipantTest() {
        // Arrange

        // Act
        ChatDto chatDto = chatService.leaveChat(chatId, userId2);

        // Assert
        assertEquals(chatId, chatDto.getChatId());
        assertTrue(chatDto.getUserIds().contains(userId1));
    }

    @Test
    @Transactional
    void getChatTest() {
        // Arrange
        Long emptyChatId = 666L;

        // Act
        ChatDto chatDto = chatService.findById(chatId);

        // Assert
        assertThrows(RuntimeException.class, () -> chatService.findById(emptyChatId));
        assertEquals(chatId, chatDto.getChatId());
    }

    @Test
    @Transactional
    void gatAllChatsTest() {
        String emptyUserId = "-1";
        assertEquals(0, chatService.findAll(emptyUserId).size());
        assertEquals(chatId, chatService.findAll(null).getFirst().getChatId());
        assertEquals(chatId, chatService.findAll(userId1).getFirst().getChatId());
    }
}
