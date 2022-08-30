package com.itea.messenger.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

//import static com.itea.messenger.entity.ChatMembersLinks.TABLE_NAME;

@Data
@Entity
@Table(name = "chats_users")

public class ChatUsersLinks {

    //public static final String TABLE_NAME = "chatmembers_table";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column (nullable = false, length = 30)
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    //    @Column (nullable = false, length = 25)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    //    added by KomSoft
    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Column(name = "view_date")
    private LocalDateTime viewDate;
}
