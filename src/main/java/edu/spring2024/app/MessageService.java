package edu.spring2024.app;

import edu.spring2024.domain.Message;
import edu.spring2024.domain.MessageStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    public Message save(Message message) {
        Message received = new Message(
                message.id(), message.chatId(), message.senderId(),
                message.recipientID(), message.content(), MessageStatus.RECEIVED
        );

        LOGGER.info("message {} saved", message.id());
        messageRepository.save(received);
        return received;
    }
}
