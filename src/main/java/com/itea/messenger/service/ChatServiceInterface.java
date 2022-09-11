package com.itea.messenger.service;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.exception.ValidationException;

import java.util.List;

public interface ChatServiceInterface {

//    void createChat (ChatsDto chatsDto, Long userId) throws ValidationException;
    ChatsDto createChat (ChatsDto chatsDto) throws ValidationException;
    void deleteChat (Long chatId);
    ChatsDto getChatById(Long chatId);
    List<ChatsDto> getAllChats();


}
