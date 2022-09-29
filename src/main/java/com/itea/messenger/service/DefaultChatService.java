package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.converter.MessagesConverter;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.entity.ChatsUsersLinks;
import com.itea.messenger.entity.Messages;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.exception.AlreadyBoundException;
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
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultChatService implements ChatsService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ChatsRepository chatsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ChatsUsersLinksRepository chatsUsersLinksRepository;
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private ChatsUsersLinksService chatUsersLinksService;
    @Autowired
    private ChatsConverter chatsConverter;
    @Autowired
    private MessagesConverter messagesConverter;

    @Override
    public ChatsDto createChat(ChatsDto chatDto) throws ValidationException {
        Chats chat = chatsConverter.chatEntityFromDto(chatDto);
        if (chatsRepository.findByName(chat.getName()).isPresent()) {
           throw new ValidationException("Chat with name \"" + chat.getName() + "\" already exists.");
        }
        return chatsConverter.chatEntityToDto(chatsRepository.save(chat));
    }

    @Transactional
    @Override
    public void deleteChat(Long chatId) throws UnexpectedRollbackException, EmptyResultDataAccessException {
        messagesService.deleteAllByChatId(chatId);
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

    @Override
    public List<ChatTypeEnum> getChatTypes() throws NotFoundException {
        try {
            return Arrays.asList(ChatTypeEnum.values());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Chat types not found!");
        }
    }

    @Transactional
    @Override
    public void addUserToChat(Long chatId, Long userId) throws NotFoundException, AlreadyBoundException {
        Chats chat = chatsRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat id:" + chatId + " not found"));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User id:" + userId + " not found"));
        if (chatsUsersLinksRepository.findByChatIdAndUserId(chatId, userId) != null) {
            throw new AlreadyBoundException("User id:" + userId + " already exists in Chat id:" + chatId);
        }
        try {
            Messages infoMessage = messagesConverter.messagesFromDto(new MessagesDto(chat.getId(), user.getId(), MessageFormat.format("User \"{0}\" has been added to chat.", user.getName())));
            chat.addMessage(infoMessage);
        } catch (ValidationException e) {
            log.error("Error creating info message with chatId:{}, userId:{}", chat.getId(), user.getId());
        }
        chat.addUser(user);
        chatsRepository.save(chat);
//      TODO - remove this when UserLogin will implement
        ChatsUsersLinks links = chatsUsersLinksRepository.findByChatIdAndUserId(chatId, userId);
        links.setViewDate(LocalDateTime.now());
        chatsUsersLinksRepository.save(links);
    }

    @Transactional
    @Override
    public void removeUserFromChat(Long chatId, Long userId) throws NotFoundException {
        Chats chat = chatsRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat id:" + chatId + " not found"));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User id:" + userId + " not found"));
        if (chatsUsersLinksRepository.findByChatIdAndUserId(chatId, userId) == null) {
            throw new NotFoundException("User id:" + userId + " is not member of Chat id:" + chatId);
        }
/*
//      May by it isn't needed
        chat.removeUser(user);
        chatsRepository.save(chat);
*/
        chatsUsersLinksRepository.deleteByChatIdAndUserId(chatId, userId);
        messagesService.deleteAllByChatIdAndUserId(chatId, userId);
//        It will be good to create message "User "{0}" has been removed from chat."
//        But it needs userId that was removed.
    }

}
