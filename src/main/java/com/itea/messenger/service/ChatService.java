package com.itea.messenger.service;

import com.itea.messenger.converter.ChatConverter;
import com.itea.messenger.dto.ChatDto;
import com.itea.messenger.entity.Chat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.itea.messenger.repository.ChatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService implements ChatServiceInterface {

    private final ChatRepository chatRepository;
    private final ChatConverter chatConverter;

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

//    @Override
//    public List<Chat> findChatList(String name) {
//        return null;
//    }

    @Override
    public List<ChatDto> getAllChats() {
        List<ChatDto> dtoList = new ArrayList<>();
        List<Chat> list = chatRepository.findAll();
        for (Chat chat : list
        ) {
            dtoList.add(chatConverter.dtoFromChatEntity(chat));
        }
        return dtoList;
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chatRepository.getChatById(chatId);
    }


}
