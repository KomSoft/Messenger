package com.itea.messenger.service;

import com.itea.messenger.entity.Chat;
import com.itea.messenger.repository.ChatRepositoryInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatService implements ChatServiceInterface {
    private final ChatRepositoryInterface chatRepository;

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

    @Override
    public Chat getChatById(Long chatId) {
        return chatRepository.findByChatId(chatId);
    }

}
