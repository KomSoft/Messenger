package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;


import java.time.LocalDateTime;
import java.util.List;

public interface MessagesService {

    MessagesDto saveMessage(MessagesDto messageDto);

    MessagesDto getMessageById(Long messageId);

    List<MessagesDto> getAllMessagesByChatId(Long chatId);


    List<MessagesDto> getAllMessagesByChatIdByStartDateAfter(Long chatId, LocalDateTime dateTime);
    void deleteMessage(Long messageId);
}
