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
                .messageStatus(message.getMessageStatus())
                .build();
    }

    public Messages messagesFromDto(MessagesDto messageDto) {
        Messages message = new Messages();
        message.setId(messageDto.getId());
        message.setChatId(messageDto.getChatId());
        message.setUserId(messageDto.getUserId());
        message.setMessageText(messageDto.getMessageText());
        message.setFileId(messageDto.getFileId());
        message.setDateTime(messageDto.getDateTime());
        message.setMessageStatus(messageDto.getMessageStatus().stream().toList());
//        message.setMessageStatus(new ArrayList<>(messageDto.getMessageStatus().stream().toList()));
        return message;
    }
}
