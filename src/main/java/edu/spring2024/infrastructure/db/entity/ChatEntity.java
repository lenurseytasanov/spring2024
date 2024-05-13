package edu.spring2024.infrastructure.db.entity;

import edu.spring2024.domain.ChatTopic;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "chat")
@Getter
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "membership",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<UserEntity> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "chat", orphanRemoval = true)
    private final Set<MessageEntity> messages = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private ChatTopic topic;
}
