package edu.spring2024.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Объект представляет комнату для обмена сообщениями между заданными пользователями.
 */
@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private final Set<User> users = new LinkedHashSet<>();

    /**
     * Сообщения, отправленные в этот чат.
     */
    @OneToMany(mappedBy = "chat")
    private final Set<Message> messages = new LinkedHashSet<>();

    /**
     * Тема обсуждения для чата
     */
    @Enumerated(EnumType.STRING)
    private ChatTopic topic;
}
