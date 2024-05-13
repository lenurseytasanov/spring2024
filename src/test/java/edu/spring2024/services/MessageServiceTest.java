package edu.spring2024.services;

import edu.spring2024.Spring2024Application;
import edu.spring2024.app.MessageService;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.ChatTopic;
import edu.spring2024.domain.Message;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.assembler.mapper.entity.ChatMapper;
import edu.spring2024.infrastructure.assembler.mapper.entity.MessageMapper;
import edu.spring2024.infrastructure.assembler.mapper.entity.UserMapper;
import edu.spring2024.infrastructure.configuration.ApplicationConfig;
import edu.spring2024.infrastructure.db.adapter.ChatAdapter;
import edu.spring2024.infrastructure.db.adapter.MessageAdapter;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import({ MessageService.class, ModelMapper.class, MessageMapper.class, UserMapper.class, ChatMapper.class,
        MessageAdapter.class, UserAdapter.class, ChatAdapter.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = {Spring2024Application.class, ApplicationConfig.class})
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

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
    void saveTest() {
        // Arrange
        String text = "content";
        Long emptyChatId = 666L;
        String emptyUserId = "666";

        // Act
        Message message = messageService.save(chatId, userId1, userId2, text);

        // Assert
        assertEquals(text, message.getContent());
        assertEquals(chatId, message.getId());
        assertEquals(userId1, message.getSender().getId());
        assertEquals(userId2, message.getRecipient().getId());

        assertThrows(RuntimeException.class, () -> messageService.save(emptyChatId, userId1, userId2, text));
        assertThrows(RuntimeException.class, () -> messageService.save(chatId, userId1, emptyUserId, text));
    }

    @Test
    @Transactional
    void getMessageTest() {
        Long emptyMessageId = 666L;

        assertThrows(RuntimeException.class, () -> messageService.findById(emptyMessageId));
    }

    @Test
    @Transactional
    void gatAllMessagesTest() {
        Long emptyChatId = 666L;

        assertEquals(0, messageService.findAll(emptyChatId).size());
        assertEquals(0, messageService.findAll(chatId).size());
        assertEquals(0, messageService.findAll(null).size());
    }
}
