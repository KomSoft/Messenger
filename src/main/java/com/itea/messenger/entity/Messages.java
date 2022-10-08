package com.itea.messenger.entity;

import com.itea.messenger.type.MessageStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    private Chats chat;
*/
    @Column(name = "chat_id")
    private Long chatId;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;
*/
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message_text", nullable = false, length = 255)
    private String messageText;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private Files file;

    @Column(name = "date_time", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "message_id")
    private List<StatusLinks> messageStatuses;


    public void addStatus(StatusLinks status) {
        messageStatuses.add(status);
//        status.setMessage(this);
    }

    public void removeStatus(StatusLinks status) {
        messageStatuses.remove(status);
//        status.setMessage(null);
    }

    public MessageStatus getStatusByUserId(Long userId) {
        for (StatusLinks status : messageStatuses) {
            if (status.getUserId().equals(userId)) {
                return status.getStatus();
            }
        }
        return null;
    }

    public MessageStatus getStatusForAuthor() {
        return getStatusByUserId(this.getUserId());
    }

    public void setAttachment(Files file) {
        if (file != null) {
            this.file = file;
//            file.setMessage(this);
        }
    }

    public void removeAttachment(Files file) {
        this.file = null;
        if (file != null) {
//            file.setMessage(null);
        }
    }

}
