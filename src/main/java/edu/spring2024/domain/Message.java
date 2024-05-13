package edu.spring2024.domain;

import lombok.*;

/**
 * Класс представляет сообщение в чате
 */
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Message {

    /**
     * автогенерируемый инентификатор сообщения
     */
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * отправитель сообщения
     */
    private User sender;

    /**
     * Получатель сообщения
     */
    private User recipient;

    /**
     * чат сообщения
     */
    private Chat chat;

    /**
     * текст сообщения
     */
    private String content;
}
