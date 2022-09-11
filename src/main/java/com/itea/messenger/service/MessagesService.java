package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.exception.ValidationException;
import java.util.List;

public interface MessagesService {

    MessagesDto saveMessage(MessagesDto messageDto) throws ValidationException;
    MessagesDto getMessageById(Long messageId) throws ValidationException;
    List<MessagesDto> getAllMessagesByChatId(Long chatId);
    List<MessagesDto> getMessagesForUserByChatId(Long chatId, Long userId);
    void deleteMessage(Long messageId) throws ValidationException;
    void deleteMessage(Long messageId, Long userId);
    void deleteAllMessagesByChatId(Long chatId);
    }
