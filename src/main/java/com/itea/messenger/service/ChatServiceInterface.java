package com.itea.messenger.service;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;

import java.util.List;

public interface ChatServiceInterface {

    void startChat (Chats chat);

    boolean deleteChat (String name);

    Chats getChatById(Long chatId);

    List<ChatsDto> getAllChats();


}
