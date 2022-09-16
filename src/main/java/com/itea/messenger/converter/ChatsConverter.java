package com.itea.messenger.converter;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.dto.ChatsShortDto;
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
            throw new ValidationException("Chat DTO is null");
        }
        if (isNull(chatsDto.getName()) || chatsDto.getName().isEmpty()) {
            throw new ValidationException("Chat name is null or empty");
        }
        if (isNull(chatsDto.getChatType()) || chatsDto.getChatType().describeConstable().isEmpty()) {
            throw new ValidationException("Chat type can't be empty");
        }
    }

    public Chats chatEntityFromDto(ChatsDto chatsDto) throws ValidationException {
        validateChat(chatsDto);
        Chats chat = new Chats();
        chat.setId(chatsDto.getId());
        chat.setName(chatsDto.getName());
        chat.setDescription(isNull(chatsDto.getDescription()) ? "" : chatsDto.getDescription());
        chat.setChatType(chatsDto.getChatType());
        return chat;
    }

    public ChatsDto chatEntityToDto(Chats chat){
        ChatsDto chatsDto = new ChatsDto();
        chatsDto.setId(chat.getId());
        chatsDto.setName(chat.getName());
        chatsDto.setDescription(chat.getDescription());
        chatsDto.setChatType(chat.getChatType());
        if (chat.getUsers() != null && chat.getUsers().size() > 0) {
            chatsDto.setUsers(chat.getUsers().stream().map(usersConverter::userToShortDto).collect(Collectors.toList()));
        }
        return chatsDto;
    }

/*
    public ChatsDto chatEntityToDto(ChatsInfo chat){
        ChatsDto chatsDto = new ChatsDto();
        chatsDto.setId(chat.getId());
        chatsDto.setName(chat.getName());
        chatsDto.setDescription(chat.getDescription());
        chatsDto.setChatType(chat.getChatType());
        if (chat.getUsers() != null && chat.getUsers().size() > 0) {
            chatsDto.setUsers(chat.getUsers().stream().map(usersConverter::userToShortDto).collect(Collectors.toList()));
        }
        return chatsDto;
    }
*/
/*
    public ChatsShortDto chatEntityToShortDto(Chats chat){
        ChatsShortDto chatsDto = new ChatsShortDto();
        chatsDto.setId(chat.getId());
        chatsDto.setName(chat.getName());
        chatsDto.setDescription(chat.getDescription());
        chatsDto.setChatType(chat.getChatType());
        return chatsDto;
    }
*/

    public ChatsShortDto chatEntityToShortDto(ChatsInfo chat){
        ChatsShortDto chatsDto = new ChatsShortDto();
        chatsDto.setId(chat.getId());
        chatsDto.setName(chat.getName());
        chatsDto.setDescription(chat.getDescription());
        chatsDto.setChatType(chat.getChatType());
        return chatsDto;
    }

}
