package edu.spring2024.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Зарегестрированный пользователь чата
 */
@Entity @Table(name = "users")
@Getter @NoArgsConstructor
public class User {

    /**
     * идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * логин пользователя
     */
    private String username;

    /**
     * пароль пользователя
     */
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
