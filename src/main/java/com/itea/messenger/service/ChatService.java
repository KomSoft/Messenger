package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.UsersRepository;
import com.itea.messenger.type.ChatTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itea.messenger.repository.ChatsRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log
@Service
@AllArgsConstructor
public class ChatService implements ChatServiceInterface {

    @Autowired
    ChatsRepository chatsRepository;
    @Autowired
    private final ChatsConverter chatsConverter;
    @Autowired
    private final MessagesService messagesService;
    @Autowired
    private final ChatUsersLinksService chatUsersLinksService;
    @Autowired
    private final UsersRepository usersRepository;
/*
    private final ChatsRepository chatsRepository;
    private final ChatsConverter chatsConverter;
    private final MessagesService messagesService;
    private final ChatUsersLinksService chatUsersLinksService;
    private final UsersRepository usersRepository;
*/

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
           messagesService.deleteAllMessagesByChatId(chatId);
           chatUsersLinksService.deleteAllByChatId(chatId);
           chatsRepository.deleteById(chatId);
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }

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

    public void addUserToChat(Long chatId, Long userId) {
//        Optional<Chats> chatsOptional = chatsRepository.findById(chatId);
        Chats chat = chatsRepository.findById(chatId).orElse(null);
        Users user = usersRepository.findById(userId).orElse(null);
//        Optional<Users> usersOptional = usersRepository.findById(userId);
        if (chat != null && user != null) {
            chat.addUser(user);
        }
/*
        if (chatsOptional.isPresent() && usersOptional.isPresent()) {
            chatsOptional.get().addUser(usersOptional.get());
        }
*/
        log.info("Can't add user id:" + userId + " to chat id:" + chatId);
    }

}
