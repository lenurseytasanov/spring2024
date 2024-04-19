package edu.spring2024.services;

import edu.spring2024.app.UserService;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.db.repository.JpaUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ UserService.class })
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @BeforeEach
    void init() {
        jpaUserRepository.save(new User("123", "user123"));
        jpaUserRepository.save(new User("456", "user456"));
        jpaUserRepository.findById("");
    }


    @Test
    void userSavingTest() {
        // Arrange
        User user1 = new User("test", "test");
        User user2 = new User("123", "new_username");

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
    void deleteTest() {
        // Arrange
        String userId1 = "123";
        String userId2 = "not_existing_id";

        // Act
        User returnedUser1 = userService.deleteUser(userId1);
        boolean isDeleted1 = jpaUserRepository.findById(returnedUser1.getId()).isEmpty();

        // Assert
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId2));
        assertEquals(returnedUser1.getId(), userId1);
        assertTrue(isDeleted1);
    }

    @Test
    void getUserTest() {
        // Arrange
        String userId1 = "123";
        String userId2 = "not_existing_id";

        // Act
        User returnedUser1 = userService.getUser(userId1);
        boolean isPresent1 = jpaUserRepository.findById(returnedUser1.getId()).isPresent();
        boolean isPresent2 = jpaUserRepository.findById(userId2).isPresent();

        // Assert
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(userId2));
        assertEquals(returnedUser1.getId(), userId1);
        assertTrue(isPresent1);
        assertFalse(isPresent2);
    }
}
