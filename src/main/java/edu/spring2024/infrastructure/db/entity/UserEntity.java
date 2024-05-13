package edu.spring2024.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "users")
@Getter
public class UserEntity {

    @Id
    private String id;

    @Column(unique = true)
    private String username;

    @ManyToMany(mappedBy = "users")
    private final Set<ChatEntity> chats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sender")
    private final Set<MessageEntity> sent = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipient")
    private final Set<MessageEntity> received = new LinkedHashSet<>();

    @PreRemove
    private void removeAssociations() {
        sent.forEach(e -> e.setSender(null));
        received.forEach(e -> e.setRecipient(null));
        chats.forEach(chat -> chat.getUsers().remove(this));
    }
}
