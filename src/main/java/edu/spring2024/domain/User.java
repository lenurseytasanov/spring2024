package edu.spring2024.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Зарегестрированный пользователь чата. Вся информация для
 * авторизации (пароль, роли, права доступа и т.д.) хранится на сервере авторизации.
 */
@Entity @Table(name = "users")
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    /**
     * Идентификатор пользователя. Совпадает с идентификатором данного польльзователя на
     * сервере авторизации.
     */
    @Id
    @NonNull
    private String id;

    /**
     * логин пользователя
     */
    @Column(unique = true)
    @NonNull
    private String username;

    /**
     * Список чатов, в которых пользователь является участником
     */
    @ManyToMany(mappedBy = "users")
    private final Set<Chat> chats = new HashSet<>();

    /**
     * Все отправленне пользователем сообщения
     */
    @OneToMany(mappedBy = "sender")
    private final Set<Message> sent = new HashSet<>();

    /**
     * Все полученные данным пользователем сообщения
     */
    @OneToMany(mappedBy = "recipient")
    private final Set<Message> received = new HashSet<>();
}
