package com.itea.messenger.dto;

import com.itea.messenger.type.MessageStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StatusLinksDto {
    private Long id;
    private Long messageId;
    private Long userId;
//    private String userName;
    private MessageStatus status;
}
