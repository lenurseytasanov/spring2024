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
    @JsonIgnore
    @ManyToOne
    private User sender;

    /**
     * получатель сообщения
     */
    @JsonIgnore
    @ManyToOne
    private User recipient;

    /**
     * текст сообщения
     */
    private String content;

    public Message(User sender, User recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }
}
