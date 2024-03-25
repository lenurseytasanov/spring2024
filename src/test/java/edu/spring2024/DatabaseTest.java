package edu.spring2024;

import edu.spring2024.app.MessageService;
import edu.spring2024.app.UserService;
import edu.spring2024.domain.Message;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.JpaMessageRepository;
import edu.spring2024.infrastructure.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private JpaUserRepository jpaUserRepository;
    @Autowired
    private JpaMessageRepository jpaMessageRepository;


    @Test
    void userSavingTest() {
        // Arrange
        User user1 = new User("user1", "pass");
        User user2 = new User("user1", "pass");

        // Act
        User returnedUser1 = userService.save(user1);
        User returnedUser2 = userService.save(user2);
        User savedUser1 = jpaUserRepository.findById(returnedUser1.getId()).orElseThrow();
        User savedUser2 = jpaUserRepository.findById(returnedUser2.getId()).orElseThrow();

        // Assert
        assertEquals(returnedUser1.getUsername(), savedUser1.getUsername());
        assertEquals(returnedUser2.getUsername(), savedUser2.getUsername());
    }

    @Test
    void messageSavingTest() {
        // Arrange
        Message message1 = new Message(null, null, "message1");
        Message message2 = new Message(null, null, "message2");

        // Act
        Message returnedMessage1 = messageService.save(message1);
        Message returnedMessage2 = messageService.save(message2);
        Message savedMessage1 = jpaMessageRepository.findById(returnedMessage1.getId()).orElseThrow();
        Message savedMessage2 = jpaMessageRepository.findById(returnedMessage2.getId()).orElseThrow();

        // Assert
        assertEquals(returnedMessage1.getContent(), savedMessage1.getContent());
        assertEquals(returnedMessage2.getContent(), savedMessage2.getContent());
    }
}
