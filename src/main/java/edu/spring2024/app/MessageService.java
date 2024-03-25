package edu.spring2024.app;

import edu.spring2024.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Сохраняет сообщение в базу данных
     * @param message отправленное сообщение
     * @return сохраненное сообщение с новым id
     */
    public Message save(Message message) {
        Message saved = messageRepository.save(message);
        log.info("message {} saved", saved.getId());
        return saved;
    }
}
