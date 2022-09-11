package com.itea.messenger.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.itea.messenger.type.MessageStatus;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Chats chat;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @Column(name = "message_text", nullable = false, length = 255)
    private String messageText;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private Files file;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StatusLinks> messageStatuses;


    public void setStatus(StatusLinks status) {
        messageStatuses.add(status);
        status.setMessage(this);
    }

    public void removeStatus(StatusLinks status) {
        messageStatuses.remove(status);
        status.setMessage(null);
    }

    public MessageStatus getStatusByUserId(Long userId) {
        for (StatusLinks status : messageStatuses) {
            if (status.getUser().getId().equals(userId)) {
                return status.getStatus();
            }
        }
        return null;
    }

    public MessageStatus getStatusForAuthor() {
        return getStatusByUserId(this.getUser().getId());
    }

    public void setAttachment(Files file) {
        this.file = file;
        file.setMessage(this);
}

    public void removeAttachment() {
        this.file = null;
    }

}
