package edu.spring2024.app;

import edu.spring2024.app.exception.ChatNotFoundException;
import edu.spring2024.app.exception.UserNotFoundException;
import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.ChatTopic;
import edu.spring2024.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для управления чатами
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    /**
     * Создает новый чат
     * @param userId1 индентификатор участника
     * @param userId2 индентификатор участника
     * @param topic тема чата
     * @return чат
     */
    @Transactional
    public Chat createChat(String userId1, String userId2, ChatTopic topic) {
        Chat chat = Chat.builder()
                .topic(topic)
                .build();

        User user1 = addUser(chat, userId1);
        User user2 = addUser(chat, userId2);

        chat = chatRepository.save(chat);
        userRepository.save(user1);
        userRepository.save(user2);
        log.debug("chat {} created", chat.getId());

        return chat;
    }

    private User addUser(Chat chat, String userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        chat.getUsers().add(user);
        user.getChats().add(chat);

        log.debug("user {} added to chat {} participants list", user.getId(), chat.getId());
        return user;
    }

    /**
     * Исключает пользователя из чата. Если список участников пуст, то удаляет чат.
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return чат
     */
    @Transactional
    public Chat leaveChat(Long chatId, String userId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.getChats().remove(chat);
        chat.getUsers().remove(user);

        if (chat.getUsers().isEmpty()) {
            chatRepository.delete(chat);
            log.debug("chat {} permanently deleted", chat.getId());
            return chat;
        }

        userRepository.save(user);
        chatRepository.save(chat);

        log.debug("user {} leave chat {}", userId, chatId);

        return chat;
    }

    /**
     * Возращает чат из бд
     * @param chatId идентификатор чата
     * @return чат
     */
    @Transactional
    public Chat findById(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    /**
     * Возваращает список чатов
     * @param userId пользователь
     * @return чаты
     */
    @Transactional
    public List<Chat> findAll(String userId) {
        if (userId == null) {
            return chatRepository.findAll();
        }

        return chatRepository.findAllByUsersId(userId);
    }
}
