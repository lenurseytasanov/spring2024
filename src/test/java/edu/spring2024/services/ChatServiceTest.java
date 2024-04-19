package edu.spring2024.services;

import edu.spring2024.app.ChatService;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.db.repository.JpaChatRepository;
import edu.spring2024.infrastructure.db.repository.JpaUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ ChatService.class })
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    private User user;

    private Chat chat;

    @BeforeEach
    void init() {
        user = new User("123", "user123");
        chat = new Chat();
        user = jpaUserRepository.save(user);
        chat = jpaChatRepository.save(chat);
        user.getChats().add(chat);
        chat.getUsers().add(user);
        user = jpaUserRepository.save(user);
        chat = jpaChatRepository.save(chat);
    }


    @Test
    void saveTest() {
        // Arrange

        // Act
        Chat chat = chatService.createChat(user.getId());
        User saved = jpaUserRepository.findById(user.getId()).orElseThrow();

        boolean containsUser = chat.getUsers().contains(user);
        boolean containsChat = saved.getChats().contains(chat);

        // Assert
        assertTrue(containsUser);
        assertTrue(containsChat);
    }

    @Test
    void deleteTest() {
        // Arrange
        Long newChatId = 999L;

        // Act
        Chat deletedChat = chatService.deleteChat(chat.getId());
        boolean containsChat = jpaUserRepository.findById(user.getId()).orElseThrow().getChats().contains(chat);

        // Assert
        assertThrows(EntityNotFoundException.class, () -> chatService.deleteChat(newChatId));
        assertEquals(chat.getId(), deletedChat.getId());
        assertFalse(containsChat);
    }

    @Test
    @Transactional
    void getUserTest() {
        // Arrange
        Long newChatId = 999L;

        // Act
        Chat returned = chatService.getChat(chat.getId());
        boolean containsChat = Objects.equals(
                jpaUserRepository.findById(user.getId())
                        .orElseThrow().getChats().stream()
                        .findFirst().orElseThrow().getId(),
                returned.getId()
        );

        System.out.println(returned.getId());
        System.out.println(chat.getId());
        System.out.println(jpaUserRepository.findById(user.getId())
                .orElseThrow().getChats().stream()
                .findFirst().orElseThrow().getId());
        // Assert
        assertThrows(EntityNotFoundException.class, () -> chatService.deleteChat(newChatId));
        assertEquals(chat.getId(), returned.getId());
        assertTrue(containsChat);
    }
}
