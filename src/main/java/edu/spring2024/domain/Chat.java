package edu.spring2024.domain;

import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Объект представляет комнату для обмена сообщениями между заданными пользователями.
 */
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Chat {

    /**
     * Уникальный идентификатор. Генерируется базой данных.
     */
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Набор участников чата способных читать и отправлять сообщения. Пока подразумевается,
     * что их не больше двух.
     */
    private final Set<User> users = new LinkedHashSet<>();

    /**
     * Сообщения, отправленные в этот чат.
     */
    private final Set<Message> messages = new LinkedHashSet<>();

    /**
     * Тема обсуждения для чата
     */
    private ChatTopic topic;
}
