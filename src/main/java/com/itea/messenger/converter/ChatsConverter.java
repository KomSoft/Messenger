package com.itea.messenger.converter;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.interfaces.ChatsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import static java.util.Objects.isNull;

@Component
public class ChatsConverter {
    @Autowired
    UsersConverter usersConverter;

    private void validateChat(ChatsDto chatsDto) throws ValidationException {
        if (isNull(chatsDto)) {
            throw new ValidationException("Chat object is null");
        }
        if (isNull(chatsDto.getName()) || chatsDto.getName().isEmpty()) {
            throw new ValidationException("[Chat] name is null or empty");
        }
        if (isNull(chatsDto.getChatType()) || chatsDto.getChatType().describeConstable().isEmpty()) {
            throw new ValidationException("Chat type can't be empty");
        }
    }

    public Chats chatEntityFromDto(ChatsDto chatsDto) throws ValidationException {
        Chats chat = new Chats();
        validateChat(chatsDto);
        chat.setId(chatsDto.getId());
        chat.setName(chatsDto.getName());
        chat.setDescription(chatsDto.getDescription());
        chat.setChatType(chatsDto.getChatType());
        return chat;
    }

    public ChatsDto chatEntityToDto(Chats chat){
        ChatsDto chatsDto = new ChatsDto();
        chatsDto.setId(chat.getId());
        chatsDto.setName(chat.getName());
        chatsDto.setDescription(chat.getDescription());
        chatsDto.setChatType(chat.getChatType());
        chatsDto.setUsers(chat.getUsers().stream().map(usersConverter::userShortToDto).collect(Collectors.toList()));
        return chatsDto;
    }

    public ChatsDto ChatEntityToDto(ChatsInfo chat){
        ChatsDto chatsDto = new ChatsDto();
        chatsDto.setId(chat.getId());
        chatsDto.setName(chat.getName());
        chatsDto.setDescription(chat.getDescription());
        chatsDto.setChatType(chat.getChatType());
//        chatsDto.setUsers(chat.getUsers().stream().map(usersConverter::userShortToDto).collect(Collectors.toList()));
        return chatsDto;
    }
}
