package edu.spring2024.services;

import edu.spring2024.app.MessageService;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.Message;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.db.repository.JpaChatRepository;
import edu.spring2024.infrastructure.db.repository.JpaMessageRepository;
import edu.spring2024.infrastructure.db.repository.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@Import({ MessageService.class })
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaMessageRepository jpaMessageRepository;

    private User user;

    private Chat chat;

    private Message message;

    @BeforeEach
    void init() {
        user = new User("123", "user123");
        chat = new Chat();
        message = new Message("content");
        user = jpaUserRepository.save(user);
        chat = jpaChatRepository.save(chat);
        message = jpaMessageRepository.save(message);
        user.getChats().add(chat);
        chat.getUsers().add(user);
        message.setChat(chat);
        message.setRecipient(user);
        message.setSender(user);
        user = jpaUserRepository.save(user);
        chat = jpaChatRepository.save(chat);
        message = jpaMessageRepository.save(message);
    }


    @Test
    void saveTest() {
        // Arrange
        Message newMessage = new Message("content2");

        // Act
        newMessage = messageService.save(newMessage, chat.getId(), user.getId(), user.getId());
        User savedUser = jpaUserRepository.findById(user.getId()).orElseThrow();
        Chat savedChat = jpaChatRepository.findById(chat.getId()).orElseThrow();

        boolean containsSender = savedUser.getSent().contains(newMessage);
        boolean containsRecipient = savedUser.getReceived().contains(newMessage);
        boolean containsChat = savedChat.getMessages().contains(newMessage);

        // Assert
        assertTrue(containsChat);
        assertTrue(containsRecipient);
        assertTrue(containsSender);
    }
}
