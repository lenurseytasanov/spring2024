package edu.spring2024.app;

import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
     * Создает и сохраняет новый чат
     * @param userId пользователь - создатель чата
     * @return новый чат
     */
    @Transactional(propagation= Propagation.REQUIRED, noRollbackFor=Exception.class)
    public Chat createChat(String userId) {
        Chat chat = new Chat();
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        chat = chatRepository.save(chat);
        user = userRepository.save(user);

        user.getChats().add(chat);
        chat.getUsers().add(user);

        chat = chatRepository.save(chat);
        userRepository.save(user);

        log.info("chat {} created", chat.getId());
        return chat;
    }

    /**
     * Удаляет чат из базы данных
     * @param id чат
     * @return удаленный чат
     */
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=Exception.class)
    public Chat deleteChat(Long id) {
        Chat chat = chatRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        chat.getUsers().forEach(user -> {
            user.getChats().remove(chat);
            userRepository.save(user);
        });
        chatRepository.delete(chat);

        log.info("chat {} deleted", chat.getId());
        return chat;
    }

    /**
     * Получает объект чата из бд
     * @param id чат
     * @return чат
     */
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=Exception.class)
    public Chat getChat(Long id) {
        return chatRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
