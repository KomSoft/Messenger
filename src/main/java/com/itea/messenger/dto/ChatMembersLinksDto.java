package com.itea.messenger.dto;

import lombok.Data;

@Data
public class ChatMembersLinksDto {
    private Long id;
    private Long chatID;
    private Long userID;
}
