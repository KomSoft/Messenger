package com.itea.messenger.entity;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class Chats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //@Length(max = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private ChatTypeEnum chatType;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Messages> chatMessages;

    @ManyToMany(mappedBy = "chats")
    private List<Users> users;

    public void addMessage(Messages message) {
        chatMessages.add(message);
        message.setChat(this);
}

    public void deleteMessage(Messages message) {
//  TODO - write logic
        //        messages.remove(message);    message.setUser(null);
    }

    public void editMessage(Messages message) {
//  TODO - write logic
        //        messages.remove(message);    message.setUser(null);
    }

}