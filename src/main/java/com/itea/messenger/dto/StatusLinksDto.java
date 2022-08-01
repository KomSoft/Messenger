package com.itea.messenger.dto;

import com.itea.messenger.type.MessageStatus;
import lombok.Data;

@Data
public class StatusLinksDto {
    private Long id;
    private MessageStatus status;
    private Long userId;
}
