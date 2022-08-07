package com.itea.messenger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime dateTime;
//    private Long statusId;
}