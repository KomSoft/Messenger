package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessagesService {

    MessagesDto saveMessage(MessagesDto messageDto) throws ValidationException, NotFoundException;
    MessagesDto getById(Long messageId) throws NotFoundException;
    List<MessagesDto> getAllMessagesByChatId(Long chatId) throws NotFoundException;
    List<MessagesDto> getMessagesForUserByChatId(Long chatId, Long userId) throws NotFoundException;
    void deleteMessage(Long messageId, Long userId) throws NotFoundException;
    void deleteAllByChatId(Long chatId);
    void deleteAllByChatIdAndUserId(Long chatId, Long userId);
    @Transactional
    MessagesDto editMessage(MessagesDto messageDto, Long userId) throws ValidationException, NotFoundException;
}
