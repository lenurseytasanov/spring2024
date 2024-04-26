package edu.spring2024.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Объект представляет комнату для обмена сообщениями между заданными пользователями.
 */
@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Chat {

    /**
     * Уникальный идентификатор. Генерируется базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Набор участников чата способных читать и отправлять сообщения. Пока подразумевается,
     * что их не больше двух.
     */
    @ManyToMany
    @JoinTable(
            name = "membership",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> users = new HashSet<>();

    /**
     * Сообщения, отправленные в этот чат.
     */
    @OneToMany(mappedBy = "chat")
    private final Set<Message> messages = new HashSet<>();

    /**
     * Тема обсуждения для чата
     */
    @Enumerated(EnumType.STRING)
    private ChatTopic topic;
}
