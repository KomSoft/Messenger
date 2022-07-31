package com.itea.messenger.service;

import com.itea.messenger.entity.Chat;

import java.util.List;

public interface ChatServiceInterface {

    public void startChat (Chat chat);

    public boolean deleteChat (String name);

    public List<Chat> findChatList (String name);
}
