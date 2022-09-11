package com.itea.messenger.dto;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class ChatsDto {
    private Long id;
    private String name;
    private String description;
    private ChatTypeEnum chatType;
    private List<UsersShortDto> users;
}
