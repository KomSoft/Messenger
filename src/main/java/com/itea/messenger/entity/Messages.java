package com.itea.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "messages")
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

    @OneToMany
    @JoinColumn(name = "message_id")
    private List<StatusLinks> messageStatus;

    public Messages(Long id, Long chatId, Long userId, String messageText, Long fileId, LocalDateTime dateTime) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
        this.messageText = messageText;
        this.fileId = fileId;
        this.dateTime = dateTime;
        this.messageStatus = new ArrayList<>();
    }

    public Messages(Long chatId, Long userId, String messageText, Long fileId) {
/*
    public Messages(Long id, Long chatId, Long userId, String messageText, Long fileId) {
        this.id = id;
*/
        this.chatId = chatId;
        this.userId = userId;
        this.messageText = messageText;
        this.fileId = fileId;
        this.dateTime = LocalDateTime.now();
        this.messageStatus = new ArrayList<>();
    }
}
