package com.itea.messenger.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chatmembers_table")


public class ChatMembersLinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column (nullable = false, length = 30)
    @Column (name = "chat_id", nullable = false)
    private Long chatId;

//    @Column (nullable = false, length = 25)
    @Column (name = "user_id", nullable = false)
    private Long userId;

//    added by KomSoft
    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Column(name = "view_date")
    private LocalDateTime viewDate;
}
