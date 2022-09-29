package com.itea.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(name = "join_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime joinDate;

    @Column(name = "view_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime viewDate;

}
