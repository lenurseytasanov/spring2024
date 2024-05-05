package edu.spring2024.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс представляет сообщение в чате
 */
@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private User sender;

    /**
     * Получатель сообщения
     */
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    /**
     * чат сообщения
     */
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    /**
     * текст сообщения
     */
    private String content;
}
