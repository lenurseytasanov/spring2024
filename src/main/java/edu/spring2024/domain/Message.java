package edu.spring2024.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Класс представляет сообщение в чате
 */
@Entity
@Getter @NoArgsConstructor
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
    private User sender;

    /**
     * чат сообщения
     */
    @ManyToOne
    private Chat chat;

    /**
     * текст сообщения
     */
    private String content;

    public Message(User sender, Chat chat, String content) {
        this.sender = sender;
        this.chat = chat;
        this.content = content;
    }
}
