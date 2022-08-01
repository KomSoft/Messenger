package com.itea.messenger.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessagesDto {
    private Long id;
    private Long chatId;
    private Long userId;
    private String messageText;
    private Long fileId;
    private LocalDateTime dateTime;
//    private Long statusId;
}