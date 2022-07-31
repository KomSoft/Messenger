package com.itea.messenger.entity;

import com.itea.messenger.enums.messageStatus.MessageStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "status_links")
@Data
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
}
