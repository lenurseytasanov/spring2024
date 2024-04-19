package edu.spring2024.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс представляет сообщение в чате
 */
@Entity
@Getter @NoArgsConstructor
@RequiredArgsConstructor
public class Message {

    /**
     * автогенерируемый инентификатор сообщения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * отправитель сообщения
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    @Setter
    private User sender;

    /**
     * Получатель сообщения
     */
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @Setter
    private User recipient;

    /**
     * чат сообщения
     */
    @ManyToOne
    @JoinColumn(name = "chat_id")
    @Setter
    private Chat chat;

    /**
     * текст сообщения
     */
    @NonNull
    private String content;
}
