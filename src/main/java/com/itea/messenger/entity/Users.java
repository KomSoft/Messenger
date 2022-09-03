package com.itea.messenger.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(length = 30)
    private String password;

    @Column(name = "photo_id")
    private Long photoId;

    @Column(unique = true, length = 30)
    private String login;

    @Column
    private int age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chats_users", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    public Set<Chats> chats;

}