package com.itea.messenger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itea.messenger.entity.StatusLinks;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
//@NoArgsConstructor
public class MessagesDto {
    private Long id;
    private Long chatId;
    private Long userId;
    private String messageText;
    private Long fileId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime dateTime;
//  Do we need to send Statuses? or we get its by another method?
//    private List<StatusLinks> messageStatus;
}