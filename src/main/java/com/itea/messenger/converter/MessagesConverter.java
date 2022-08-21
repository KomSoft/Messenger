package com.itea.messenger.converter;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.Messages;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MessagesConverter {
    public MessagesDto messagesToDto(Messages message) {
        return MessagesDto.builder().id(message.getId()).chatId(message.getChatId()).userId(message.getUserId())
                .messageText(message.getMessageText()).fileId(message.getFileId())
                .dateTime(message.getDateTime())
                .messageStatus(message.getMessageStatus()).build();
//                .statusId(message.getStatusId()).build();
/*
        MessagesDto messageDto = new MessagesDto();
        messageDto.setId(message.getId());
        messageDto.setChatId(message.getChatId());
        messageDto.setUserId(message.getUserId());
        messageDto.setFileId(message.getFileId());
        messageDto.setMessageText(message.getMessageText());
        messageDto.setDateTime(message.getDateTime());
        return messageDto;
*/
    }

    public Messages messagesFromDto(MessagesDto messageDto) {
        Messages message = new Messages();
        message.setId(messageDto.getId());
        message.setChatId(messageDto.getChatId());
        message.setUserId(messageDto.getUserId());
        message.setMessageText(messageDto.getMessageText());
        message.setFileId(messageDto.getFileId());
        message.setDateTime(messageDto.getDateTime());
        message.setMessageStatus(messageDto.getMessageStatus());
//        message.getMessageStatus() = new ArrayList<>(messageDto.getMessageStatus().stream().toList());
//        message.setStatusId(messageDto.getStatusId());
        return message;
    }
}
