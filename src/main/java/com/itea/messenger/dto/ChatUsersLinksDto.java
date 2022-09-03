package com.itea.messenger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatUsersLinksDto {
    private Long id;
    private Long chatId;
    private Long userId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime joinDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime viewDate;

    public ChatUsersLinksDto(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }


}
