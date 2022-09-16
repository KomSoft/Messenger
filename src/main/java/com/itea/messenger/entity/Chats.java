package com.itea.messenger.entity;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class Chats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

//    @Length(max = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private ChatTypeEnum chatType;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Messages> chatMessages;

    @ManyToMany(mappedBy = "chats", fetch = FetchType.LAZY)
    private Set<Users> users;

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

    public void addUser(Users user) {
        users.add(user);
        user.chats.add(this);
    }

}