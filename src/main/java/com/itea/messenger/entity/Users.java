package com.itea.messenger.entity;

import lombok.*;
//import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor

public class Users {
    public final static int MIN_AGE = 9;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(length = 30)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Files avatar;

    @Column(unique = true, length = 30)
    private String login;

    @Min(value = 10, message = "Age should not be less than 10")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chats_users", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    public Set<Chats> chats;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Messages> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StatusLinks> statusLinks;

    public void addMessage(Messages message) {
      messages.add(message);
      message.setUser(this);
    }

    public void deleteMessage(Messages message) {
//  TODO - write logic
        //        messages.remove(message);    message.setUser(null);
    }

    public void editMessage(Messages message) {
//  TODO - write logic
        //        messages.remove(message);    message.setUser(null);
    }

    public void setAvatar(@NotNull Files file) {
        this.avatar = file;
        file.setUser(this);
    }

    public void removeAvatar(@NotNull Files file) {
        this.avatar = null;
        file.setUser(null);
    }
//    TODO - add/ remove StatusLinks. Can we use messageId or other?
}