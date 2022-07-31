package com.itea.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages_table")
@Data
@NoArgsConstructor
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "message_text", length = 255)
    private String messageText;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
/*

    @Column(name = "status_id")
    private Long statusId;
*/

}
