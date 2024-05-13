package edu.spring2024.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "message")
@Getter
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @Setter
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserEntity recipient;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    private String content;

    @PreRemove
    private void removeAssociations() {
        sender.getSent().remove(this);
        recipient.getReceived().remove(this);
    }
}
