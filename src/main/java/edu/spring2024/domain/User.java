package edu.spring2024.domain;

import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Зарегестрированный пользователь чата. Вся информация для
 * авторизации (пароль, роли, права доступа и т.д.) хранится на сервере авторизации.
 */
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    /**
     * Идентификатор пользователя. Совпадает с идентификатором данного польльзователя на
     * сервере авторизации.
     */
    @EqualsAndHashCode.Include
    private String id;

    /**
     * логин пользователя
     */
    private String username;

    /**
     * Список чатов, в которых пользователь является участником
     */
    private final Set<Chat> chats = new LinkedHashSet<>();

    private final Set<Message> sent = new LinkedHashSet<>();

    private final Set<Message> received = new LinkedHashSet<>();
}
