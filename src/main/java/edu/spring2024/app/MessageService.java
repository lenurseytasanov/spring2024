package edu.spring2024.app;

import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.app.port.MessageRepository;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.Message;
import edu.spring2024.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для управления сообщениями
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    /**
     * Сохраняет сообщение в базу данных
     * @param message сообщение
     * @param chatId чат, которому принадлежит сообщение
     * @param senderId отправитель
     * @param recipientId получатель
     * @return сохраненное сообщение
     */
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor=Exception.class)
    public Message save(Message message, Long chatId, String senderId, String recipientId) {
        User sender = userRepository.findById(senderId).orElseThrow(EntityNotFoundException::new);
        User recipient = userRepository.findById(recipientId).orElseThrow(EntityNotFoundException::new);
        Chat chat = chatRepository.findById(chatId).orElseThrow(EntityNotFoundException::new);

        Message saved = messageRepository.save(message);
        sender.getSent().add(saved);
        recipient.getReceived().add(saved);
        chat.getMessages().add(saved);

        saved.setSender(sender);
        saved.setRecipient(recipient);
        saved.setChat(chat);

        saved = messageRepository.save(saved);
        userRepository.save(sender);
        userRepository.save(recipient);
        chatRepository.save(chat);

        log.info("message {} saved", saved.getId());
        return saved;
    }
}
