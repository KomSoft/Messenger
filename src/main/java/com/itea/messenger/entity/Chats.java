package com.itea.messenger.entity;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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

    @ManyToMany(mappedBy = "chats")
    private List<Users> users;
//    private Set<Users> users;

    public Chats(Long id, String name, String description, ChatTypeEnum chatType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.chatType = chatType;
        users = new ArrayList<>();
        chatMessages = new ArrayList<>();
    }
/*

    public Chats(String name, String description, ChatTypeEnum chatType) {
        this.name = name;
        this.description = description;
        this.chatType = chatType;
        users = new ArrayList<>();
        chatMessages = new ArrayList<>();
    }
*/

}