package com.itea.messenger.entity;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.*;

import javax.persistence.*;
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

//    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Messages> chatMessages;

    @ManyToMany(mappedBy = "chats", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Users> users;

    public void addMessage(Messages message) {
        chatMessages.add(message);
//        message.setChat(this);
    }

    public void addUser(Users user) {
        users.add(user);
        user.chats.add(this);
    }

/*
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final SettingPresets that = (SettingPresets) o;
        return id != null && Objects.equals(id, that.id);
    }
*/

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}