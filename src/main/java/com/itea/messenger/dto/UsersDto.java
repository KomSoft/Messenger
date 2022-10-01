package com.itea.messenger.dto;

import lombok.Data;

@Data
public class UsersDto {
    private Long id;
    private String name;
    private String password;
    private String login;
    private String role;
    private Long avatarId;
    private String avatarName;
    private int age;

}