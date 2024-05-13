package edu.spring2024.app;

import edu.spring2024.app.exception.ChatNotFoundException;
import edu.spring2024.app.exception.MessageNotFoundException;
import edu.spring2024.app.exception.UserNotFoundException;
import edu.spring2024.app.port.ChatRepository;
import edu.spring2024.app.port.MessageRepository;
import edu.spring2024.app.port.UserRepository;
import edu.spring2024.domain.Chat;
import edu.spring2024.domain.Message;
import edu.spring2024.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * @param chatId чат, которому принадлежит сообщение
     * @param senderId отправитель
     * @param recipientId получатель
     * @param content текст сообщения
     * @return сохраненное сообщение
     */
    @Transactional
    public Message save(Long chatId, String senderId, String recipientId, String content) {

        User sender = userRepository.findById(senderId).orElseThrow(UserNotFoundException::new);
        User recipient = userRepository.findById(recipientId).orElseThrow(UserNotFoundException::new);
        Chat chat = chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);

        Message message = Message.builder()
                .chat(chat)
                .recipient(recipient)
                .sender(sender)
                .content(content)
                .build();

        chat.getMessages().add(message);
        sender.getSent().add(message);
        recipient.getReceived().add(message);

        message = messageRepository.save(message);
        chatRepository.save(chat);
        userRepository.save(sender);
        userRepository.save(recipient);

        log.info("message {} saved", message.getId());
        return message;
    }

    /**
     * Возвращает список сообщений
     * @param chatId чат
     * @return сообщения
     */
    @Transactional
    public List<Message> findAll(Long chatId) {
        if (chatId == null) {
            return messageRepository.findAll();
        }

        return messageRepository.findAllByChatId(chatId);
    }

    /**
     * Возвращает сообщение по id
     * @param id id сообщения
     * @return сообщение
     */
    @Transactional
    public Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
    }
}
