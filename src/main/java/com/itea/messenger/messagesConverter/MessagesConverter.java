package com.itea.messenger.messagesConverter;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.Messages;
import org.springframework.stereotype.Component;

@Component
public class MessagesConverter {
    public MessagesDto messagesToDto(Messages message) {
        return MessagesDto.builder().id(message.getId()).chatId(message.getChatId()).userId(message.getUserId())
                .messageText(message.getMessageText()).fileId(message.getFileId())
//              need to convert date & time ?
                .dateTime(message.getDateTime())
                .statusId(message.getStatusId()).build();
    }

    public Messages messagesFromDto(MessagesDto messageDto) {
/*
        return Messages.builder().id(messageDto.getId()).chatId(messageDto.getChatId()).userId(messageDto.getUserId())
                .messageText(messageDto.getMessageText()).fileId(messageDto.getFileId())
                .dateTime(messageDto.getDateTime())
                .statusId(messageDto.getStatusId()).build();
*/
        Messages message = new Messages();
        message.setId(messageDto.getId());
        message.setChatId(messageDto.getChatId());
        message.setUserId(messageDto.getUserId());
        message.setMessageText(messageDto.getMessageText());
        message.setFileId(messageDto.getFileId());
        message.setDateTime(messageDto.getDateTime());
        message.setStatusId(messageDto.getStatusId());
        return message;
    }
/*
        private Long id;
        private Long chatId;
        private Long userId;
        private String messageText;
        private Long fileId;
        private LocalDate dateTime;
        private Long statusId;
*/
}
