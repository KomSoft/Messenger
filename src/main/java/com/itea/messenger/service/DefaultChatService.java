package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.ChatsUsersLinks;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.ChatsUsersLinksRepository;
import com.itea.messenger.repository.UsersRepository;
import com.itea.messenger.type.ChatTypeEnum;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.itea.messenger.repository.ChatsRepository;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultChatService implements ChatsService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    ChatsRepository chatsRepository;
    @Autowired
    ChatsConverter chatsConverter;
    @Autowired
    MessagesService messagesService;
    @Autowired
    ChatsUsersLinksService chatUsersLinksService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ChatsUsersLinksRepository chatUsersLinksRepository;


    @Override
//    public void createChat(ChatsDto chatDto, Long userId) throws ValidationException {
    public ChatsDto createChat(ChatsDto chatDto) throws ValidationException {
        Chats chat = chatsConverter.chatEntityFromDto(chatDto);
        if (chatsRepository.findByName(chat.getName()).isPresent()) {
           throw new ValidationException("Chat with name \"" + chat.getName() + "\" already exists.");
        }
        return chatsConverter.chatEntityToDto(chatsRepository.save(chat));
    }

    @Override
    public void deleteChat(Long chatId) throws EmptyResultDataAccessException {
        messagesService.deleteAllMessagesByChatId(chatId);
        chatUsersLinksService.deleteAllByChatId(chatId);
        chatsRepository.deleteById(chatId);
    }

    @Override
    public List<ChatsDto> getAllChats() throws NotFoundException {
        List<Chats> chatsList = chatsRepository.findAll();
        try {
            return chatsList.stream().map(chatsConverter::chatEntityToDto).collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new NotFoundException("NullPointerException. Chats not found!");
        }
    }

    @Override
    public ChatsDto getChatById(Long chatId) throws NotFoundException {
        return chatsConverter.chatEntityToDto(chatsRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat with id:" + chatId + " not found")));
    }

    public List<ChatTypeEnum> getChatTypes() {
        return Arrays.asList(ChatTypeEnum.values());
    }

    public void addUserToChat(Long chatId, Long userId) throws NotFoundException {
        Chats chat = chatsRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat with id:" + chatId + " not found"));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id:" + userId + " not found"));
        chat.addUser(user);
        ChatsUsersLinks temp = new ChatsUsersLinks(chat.getId(), user.getId());
//      TODO - why  it doesn't work?
//  org.postgresql.util.PSQLException: ПОМИЛКА: null значення в стовпці "join_date" відношення "chats_users" порушує not-null обмеження
//  Detail: Помилковий рядок містить (35, 20, 1, null, null).   ???!!!
//  joinDate set to LocalDateTime.now() in constructor ChatUsersLinks(Long chatId, Long userId)
//
//        chatUsersLinksRepository.save(temp);
//        chatsRepository.save(chat);
    }

}
