package com.itea.messenger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itea.messenger.type.FileTypes;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessagesDto {
    private Long id;
    private Long chatId;
    private String chatName;
    private Long userId;
//    private String userName;
    private String messageText;
    private Long fileId;
    private String fileName;
    private FileTypes fileType;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
    private List<StatusLinksDto> statuses;

    public MessagesDto(Long chatId, Long userId, String text) {
        this.chatId = chatId;
        this.userId = userId;
        this.messageText = text;
        this.dateTime = LocalDateTime.now();
        this.statuses = new ArrayList<>();
    }

}