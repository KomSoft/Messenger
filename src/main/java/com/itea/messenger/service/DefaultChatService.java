package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;
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
    private ChatsConverter chatsConverter;
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private ChatsUsersLinksService chatUsersLinksService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ChatsUsersLinksRepository chatUsersLinksRepository;

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

    public List<ChatTypeEnum> getChatTypes() throws NotFoundException {
        try {
            return Arrays.asList(ChatTypeEnum.values());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Chat types not found!");
        }
    }

    @Transactional
    public void addUserToChat(Long chatId, Long userId) throws NotFoundException, AlreadyBoundException {
        if (chatUsersLinksRepository.findByChatIdAndUserId(chatId, userId) != null) {
            throw new AlreadyBoundException("User id:" + userId + " already exists in Chat id:" + chatId);
        }
        Chats chat = chatsRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat id:" + chatId + " not found"));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User id:" + userId + " not found"));
        chat.addUser(user);
        chatsRepository.save(chat);
    }

}
