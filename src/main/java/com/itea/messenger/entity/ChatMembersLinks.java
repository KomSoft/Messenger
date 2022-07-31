package com.itea.messenger.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "chatmembers_table")


public class ChatMembersLinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 30)
    private Long chatID;

    @Column (nullable = false, length = 25)
    private Long userID;
}
