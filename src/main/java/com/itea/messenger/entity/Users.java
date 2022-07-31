package com.itea.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_table")
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

}