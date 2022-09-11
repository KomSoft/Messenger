package com.itea.messenger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itea.messenger.type.FileTypes;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessagesDto {
    private Long id;
    private Long chatId;
    private String chatName;
    private Long userId;
    private String userName;
    private String messageText;
    private Long fileId;
    private String fileName;
    private FileTypes fileType;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
    private List<StatusLinksDto> statuses;
}