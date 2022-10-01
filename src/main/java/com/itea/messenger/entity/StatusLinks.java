package com.itea.messenger.entity;

import com.itea.messenger.type.MessageStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "status_links")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StatusLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_type")
    private MessageStatus status;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message_id")
    private Long messageId;

    public StatusLinks(Long userId, MessageStatus status) {
        this.userId = userId;
        this.status = status;
    }
}
