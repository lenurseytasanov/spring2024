package edu.spring2024.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Зарегестрированный пользователь чата. Вся информация для
 * авторизации (пароль, роли, права доступа и т.д.) хранится на сервере авторизации.
 */
@Entity @Table(name = "users")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    /**
     * Идентификатор пользователя. Совпадает с идентификатором данного польльзователя на
     * сервере авторизации.
     */
    @Id
    private String id;

    /**
     * логин пользователя
     */
    @Column(unique = true)
    private String username;

    /**
     * Список чатов, в которых пользователь является участником
     */
    @ManyToMany(mappedBy = "users")
    private final Set<Chat> chats = new LinkedHashSet<>();

    /**
     * Все отправленне пользователем сообщения
     */
    @OneToMany(mappedBy = "sender")
    private final Set<Message> sent = new LinkedHashSet<>();

    /**
     * Все полученные данным пользователем сообщения
     */
    @OneToMany(mappedBy = "recipient")
    private final Set<Message> received = new LinkedHashSet<>();
}
