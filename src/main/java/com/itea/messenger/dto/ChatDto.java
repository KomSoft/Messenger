package com.itea.messenger.dto;

import com.itea.messenger.type.ChatTypeEnum;
import lombok.Data;

@Data
public class ChatDto {
    private Long chatId;
    private String name;
    private String description;
    private ChatTypeEnum chatType;
}
