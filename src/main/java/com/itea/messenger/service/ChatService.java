package com.itea.messenger.service;

import com.itea.messenger.entity.Chat;

import java.util.List;

public class ChatService implements ChatServiceInterface {
    @Override
    public void startChat(Chat chat) {

    }

    @Override
    public boolean deleteChat(String name) {
        return false;
    }

    @Override
    public List<Chat> findChatList(String name) {
        return null;
    }
}
