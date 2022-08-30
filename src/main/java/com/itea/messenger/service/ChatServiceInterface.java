package com.itea.messenger.service;

import com.itea.messenger.entity.Chat;

import java.util.List;

public interface ChatServiceInterface {

    void startChat (Chat chat);

    boolean deleteChat (String name);

    List<Chat> findChatList (String name);

    Chat getChatById(Long chatId);

}
