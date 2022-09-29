package com.itea.messenger.service;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.exception.AlreadyBoundException;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.type.ChatTypeEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatsService {

    ChatsDto createChat (ChatsDto chatsDto) throws ValidationException;
    void deleteChat (Long chatId);
    ChatsDto getChatById(Long chatId) throws NotFoundException;
    List<ChatsDto> getAllChats() throws NotFoundException;
    List<ChatTypeEnum> getChatTypes() throws NotFoundException;
    @Transactional
    void addUserToChat(Long chatId, Long userId) throws NotFoundException, AlreadyBoundException;
    @Transactional
    void removeUserFromChat(Long chatId, Long userId) throws NotFoundException;
}
