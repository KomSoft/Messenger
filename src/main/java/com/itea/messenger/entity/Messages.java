package com.itea.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "messages_table")
@Data
//@NoArgsConstructor
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @Column(name = "status_id")
    private Long statusId;

}
