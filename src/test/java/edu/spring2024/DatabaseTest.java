package edu.spring2024;

import edu.spring2024.app.AuthService;
import edu.spring2024.app.MessageRepository;
import edu.spring2024.app.MessageService;
import edu.spring2024.app.UserRepository;
import edu.spring2024.domain.ChatUser;
import edu.spring2024.domain.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;


    @Test
    void userSavingTest() {
        // Arrange
        ChatUser user1 = new ChatUser();
        user1.setUsername("user1");
        ChatUser user2 = new ChatUser();
        user2.setUsername("user2");

        // Act
        ChatUser returnedUser1 = authService.save(user1);
        ChatUser returnedUser2 = authService.save(user2);
        ChatUser savedUser1 = userRepository.findById(returnedUser1.getId()).orElseThrow();
        ChatUser savedUser2 = userRepository.findById(returnedUser2.getId()).orElseThrow();

        // Assert
        assertEquals(returnedUser1.getUsername(), savedUser1.getUsername());
        assertEquals(returnedUser2.getUsername(), savedUser2.getUsername());
    }

    @Test
    void messageSavingTest() {
        // Arrange
        Message message1 = new Message();
        message1.setContent("message1");
        Message message2 = new Message();
        message2.setContent("message2");

        // Act
        Message returnedMessage1 = messageService.save(message1);
        Message returnedMessage2 = messageService.save(message2);
        Message savedMessage1 = messageRepository.findById(returnedMessage1.getId()).orElseThrow();
        Message savedMessage2 = messageRepository.findById(returnedMessage2.getId()).orElseThrow();

        // Assert
        assertEquals(returnedMessage1.getContent(), savedMessage1.getContent());
        assertEquals(returnedMessage2.getContent(), savedMessage2.getContent());
    }
}
