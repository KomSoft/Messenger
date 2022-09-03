package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.itea.messenger.repository.ChatsRepository;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class ChatService implements ChatServiceInterface {

    private final ChatsRepository chatsRepository;
    private final ChatsConverter chatsConverter;
    private final MessagesService messagesService;
    private final ChatUsersLinksService chatUsersLinksService;

    private void validateChat(ChatsDto chatsDto, Long userId) throws ValidationException {
        if (isNull(chatsDto)) {
            throw new ValidationException("Chat object is null");
        }
        if (isNull(chatsDto.getChatType()) || chatsDto.getChatType().describeConstable().isEmpty()) {
            throw new ValidationException("Chat type can't be empty");
        }
        if (isNull(userId)) {
            throw new ValidationException("User id can't be empty");
        }
    }

    @Override
    public void createChat(ChatsDto chatDto, Long userId) throws ValidationException {
        validateChat(chatDto, userId);
        final Long chatId = chatsRepository.save(chatsConverter.chatEntityFromDto(chatDto)).getId();
        chatUsersLinksService.saveChatUsersLink(new ChatUsersLinksDto(chatId, userId));
    }

    @Override
    public void deleteChat(Long chatId){
       try {
           chatUsersLinksService.deleteAllByChatId(chatId);
           messagesService.deleteAllMessagesByChatId(chatId);
           chatsRepository.deleteById(chatId);
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }

//    TODO - public ? addChatUser(userId)

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
    public ChatsDto getChatById(Long chatId) {
        return chatsConverter.dtoFromChatEntity(chatsRepository.getChatById(chatId));
//      Below gets us recursion. Changed result type to ChatsDto
//        return (Chats) chatsRepository.getChatById(chatId);
    }

}
