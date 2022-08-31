package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.itea.messenger.repository.ChatsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService implements ChatServiceInterface {

    private final ChatsRepository chatsRepository;
    private final ChatsConverter chatsConverter;

    @Override
    public void startChat(Chats chat) {
    }

    @Override
    public boolean deleteChat(String name) {
        return false;
    }

    @Override
    public List<ChatsDto> getAllChats() {
        List<ChatsDto> dtoList = new ArrayList<>();
        List<Chats> list = chatsRepository.findAll();
        for (Chats chat : list) {
            dtoList.add(chatsConverter.dtoFromChatEntity(chat));
        }
        return dtoList;
    }

    @Override
    public Chats getChatById(Long chatId) {
        return chatsRepository.getChatById(chatId);
    }

}
