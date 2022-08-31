package com.itea.messenger.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chats_users")

public class ChatUsersLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Column(name = "view_date")
    private LocalDateTime viewDate;
}
