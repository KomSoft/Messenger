package com.itea.messenger.converter;

import com.itea.messenger.dto.ChatDto;
import com.itea.messenger.entity.Chat;
import org.springframework.stereotype.Component;

@Component
public class ChatConverter {
    public Chat chatEntityFromDto(ChatDto chatDto) {
        Chat chat = new Chat();
        chat.setId(chatDto.getChatId());
        chat.setName(chatDto.getName());
        chat.setDescription(chat.getDescription());
        return chat;
    }

    public ChatDto dtoFromChatEntity(Chat chat){
        ChatDto chatDto = new ChatDto();
        chatDto.setChatId(chat.getId());
        chatDto.setName(chat.getName());
        chatDto.setDescription(chat.getDescription());
        return chatDto;
    }
}
