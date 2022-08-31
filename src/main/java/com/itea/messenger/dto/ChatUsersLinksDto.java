package com.itea.messenger.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class ChatUsersLinksDto {
    private Long id;
    private Long chatId;
    private Long userId;
    private LocalDateTime joinDate;
    private LocalDateTime viewDate;
}
