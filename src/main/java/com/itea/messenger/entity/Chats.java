package com.itea.messenger.entity;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;
import java.util.ArrayList;
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

    @OneToMany
    @JoinColumn(name = "chat_id")
    private List<Messages> chatMessages;

    @ManyToMany
    @JoinTable(name = "chats_users", joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> users;
//    private Set<Users> users;
/*
    @OneToOne(mappedBy = "chat")
    public Users user;

*/
    public Chats(String name, String description, ChatTypeEnum chatType) {
        this.name = name;
        this.description = description;
        this.chatType = chatType;
        chatMessages = new ArrayList<>();
    }

}