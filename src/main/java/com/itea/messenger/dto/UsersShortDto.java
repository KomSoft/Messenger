package com.itea.messenger.dto;

import lombok.Data;

@Data
public class UsersShortDto {
    private Long id;
    private String name;
    private Long avatarId;
    private String avatarName;
}