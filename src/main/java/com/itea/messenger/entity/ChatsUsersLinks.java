package com.itea.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chats_users")
public class ChatsUsersLinks {

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

    public ChatsUsersLinks(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
        this.joinDate = LocalDateTime.now();
    }

/*
    public ChatUsersLinks(Long id, Long chatId, Long userId, LocalDateTime joinDate, LocalDateTime viewDate) {
        this.chatId = chatId;
        this.userId = userId;
        this.joinDate = LocalDateTime.now();
    }
*/
}
