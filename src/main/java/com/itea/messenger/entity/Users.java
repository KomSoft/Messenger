package com.itea.messenger.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
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

    @Min(value = 10, message = "Age should not be less than "+ MIN_AGE)
    private int age;

    @Column(columnDefinition = "VARCHAR(30) DEFAULT 'USER'")
    private String role;

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

    public void setAvatar(Files file) {
        if (file != null) {
            this.avatar = file;
            file.setUser(this);
        }
    }

    public void removeAvatar(Files file) {
        this.avatar = null;
        if (file != null) {
            file.setUser(null);
        }
    }
}