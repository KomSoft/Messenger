package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface MessagesService {

    MessagesDto saveMessage(MessagesDto messageDto);

    MessagesDto getMessageById(Long messageId);

    List<MessagesDto> getAllMessagesByChatId(Long chatId);

    void deleteMessage(Long messageId);
}
