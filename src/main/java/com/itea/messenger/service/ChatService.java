package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.UsersRepository;
import com.itea.messenger.type.ChatTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.itea.messenger.repository.ChatsRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService implements ChatServiceInterface {

    private final ChatsRepository chatsRepository;
    private final ChatsConverter chatsConverter;
    private final MessagesService messagesService;
    private final ChatUsersLinksService chatUsersLinksService;
    private final UsersRepository usersRepository;

    @Override
//    public void createChat(ChatsDto chatDto, Long userId) throws ValidationException {
    public ChatsDto createChat(ChatsDto chatDto) throws ValidationException {
//        validateChat(chatDto, userId);
/*
        Optional<Users> user = usersRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ValidationException("[createChat] userId:" + userId + " is incorrect");
        }
        final Long chatId = chatsRepository.save(chatsConverter.chatEntityFromDto(chatDto)).getId();
        chatUsersLinksService.saveChatUsersLink(new ChatUsersLinks(chatId, userId));
*/
        Chats chat = chatsRepository.save(chatsConverter.chatEntityFromDto(chatDto));
        return chatsConverter.chatEntityToDto(chat);
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
            dtoList.add(chatsConverter.chatEntityToDto(chat));
        }
        return dtoList;
    }

    @Override
    public ChatsDto getChatById(Long chatId) {
        return chatsConverter.chatEntityToDto(chatsRepository.findById(chatId).orElse(null));
//      Below gets us recursion. Changed result type to ChatsDto
//        return (Chats) chatsRepository.getChatById(chatId);
    }

    public List<ChatTypeEnum> getChatTypes() {
        List<ChatTypeEnum> typesOfChat = Arrays.asList(ChatTypeEnum.values());
        return typesOfChat;
    }
}
