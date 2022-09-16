package com.itea.messenger.service;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;

import java.util.List;

public interface ChatsService {

//    void createChat (ChatsDto chatsDto, Long userId) throws ValidationException;
    ChatsDto createChat (ChatsDto chatsDto) throws ValidationException;
    void deleteChat (Long chatId);
    ChatsDto getChatById(Long chatId) throws NotFoundException;
    List<ChatsDto> getAllChats() throws NotFoundException;


}
