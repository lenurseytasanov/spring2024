package edu.spring2024.app;

import edu.spring2024.app.dto.chat.ChatDto;
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

    private ChatDto convertChatToDto(Chat chat) {
        List<String> users = chat.getUsers().stream().map(User::getId).toList();
        return new ChatDto(chat.getId(), users, chat.getTopic());
    }


    /**
     * Создает новый чат
     * @param userId1 индентификатор участника
     * @param userId2 индентификатор участника
     * @param topic тема чата
     * @return чат
     */
    @Transactional
    public ChatDto createChat(String userId1, String userId2, ChatTopic topic) {
        Chat chat = Chat.builder()
                .topic(topic)
                .build();
        chat = chatRepository.save(chat);

        User user1 = userRepository.findById(userId1).orElseThrow();
        User user2 = userRepository.findById(userId2).orElseThrow();

        addUser(chat, user1);
        addUser(chat, user2);

        chat = chatRepository.findById(chat.getId()).orElseThrow();
        log.debug("chat {} created", chat.getId());

        return convertChatToDto(chat);
    }

    @Transactional
    private void addUser(Chat chat, User user) {
        chat.getUsers().add(user);
        user.getChats().add(chat);

        chatRepository.save(chat);
        userRepository.save(user);
        log.debug("user {} added to chat {} participants list", user.getId(), chat.getId());
    }

    /**
     * Исключает пользователя из чата. Если список участников пуст, то удаляет чат.
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return чат
     */
    @Transactional
    public ChatDto leaveChat(Long chatId, String userId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        user.getChats().remove(chat);
        chat.getUsers().remove(user);

        if (chat.getUsers().isEmpty()) {
            chatRepository.delete(chat);
            log.debug("chat {} permanently deleted", chat.getId());
            return convertChatToDto(chat);
        }

        chatRepository.save(chat);
        userRepository.save(user);
        log.debug("user {} leave chat {}", userId, chatId);

        return convertChatToDto(chat);
    }

    /**
     * Возращает чат из бд
     * @param chatId идентификатор чата
     * @return чат
     */
    @Transactional
    public ChatDto getChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        return convertChatToDto(chat);
    }
}
