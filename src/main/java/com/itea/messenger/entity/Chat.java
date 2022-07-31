package com.itea.messenger.entity;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(nullable = false)
    private String name;

    //@Length(max = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private ChatTypeEnum chatType;

    public Chat(String name, String description, ChatTypeEnum chatType) {
        this.name = name;
        this.description = description;
        this.chatType = chatType;
    }
}