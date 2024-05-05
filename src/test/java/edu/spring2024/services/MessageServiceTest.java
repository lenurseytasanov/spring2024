package edu.spring2024.services;

import edu.spring2024.app.MessageService;
import edu.spring2024.app.dto.message.MessageDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import({ MessageService.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

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
    void saveTest() {
        // Arrange
        String text = "content";
        Long emptyChatId = 666L;
        String emptyUserId = "666";

        // Act
        MessageDto messageDto = messageService.save(chatId, userId1, userId2, text);

        // Assert
        assertEquals(text, messageDto.content());
        assertEquals(chatId, messageDto.chatId());
        assertEquals(userId1, messageDto.senderId());
        assertEquals(userId2, messageDto.recipientId());

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
