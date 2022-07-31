package com.itea.messenger.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MessagesDto {
    private Long id;
    private Long chatId;
    private Long userId;
    private String messageText;
    private Long fileId;
    private LocalDate dateTime;
    private Long statusId;

}
