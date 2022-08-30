package com.itea.messenger.dto;

import lombok.Data;

@Data
public class ChatMembersLinksDto {
    private Long id;
    private Long chatId;
    private Long userId;
}
