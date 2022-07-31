package com.itea.messenger.dto;

import lombok.Data;

@Data
public class UsersDto {
    private Long id;
    private String name;
    private String password;
    private Long photoId;
    private String login;
    private int age;
}