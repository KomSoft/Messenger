package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.converter.ChatUsersLinksConverter;
import com.itea.messenger.converter.UsersConverter;
import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.interfaces.ChatInfo;
import com.itea.messenger.interfaces.UserInfo;
import com.itea.messenger.repository.ChatUsersLinksRepository;
import com.itea.messenger.repository.ChatsRepository;
import com.itea.messenger.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultChatUsersLinksService implements ChatUsersLinksService {
    private final ChatUsersLinksRepository chatUsersLinksRepository;
    private final ChatUsersLinksConverter chatUsersLinksConverter;
    private final ChatsRepository chatsRepository;
    private final UsersRepository usersRepository;
    private final UsersConverter usersConverter;
    private final ChatsConverter chatsConverter;

    private void validateChatUsersLinkDto(ChatUsersLinksDto chatUsersLinksDto)throws ValidationException {
        if (isNull(chatUsersLinksDto)) {
            throw new ValidationException("Object chat users link is null");
        }
        if (isNull(chatUsersLinksDto.getChatId()) || isNull(chatUsersLinksDto.getUserId())) {
            throw new ValidationException("Chat id or user id is null");
        }
    }
    @Override
    public ChatUsersLinksDto saveChatUsersLink(ChatUsersLinksDto chatUsersLinksDto) throws ValidationException {
        validateChatUsersLinkDto(chatUsersLinksDto);
        ChatUsersLinks chatUsersLinks = chatUsersLinksRepository.save(chatUsersLinksConverter.chatUsersLinksFromDto(chatUsersLinksDto));
        return chatUsersLinksConverter.dtoFromChatUsersLinks(chatUsersLinks);
    }

    @Override
    public ChatUsersLinksDto findById(Long id) throws ValidationException {
        ChatUsersLinks chatUsersLinks = chatUsersLinksRepository.findById(id).orElseThrow(() ->new ValidationException("No object with this id"));
        return chatUsersLinksConverter.dtoFromChatUsersLinks(chatUsersLinks);
    }

    @Override
    public List<ChatUsersLinksDto> findAll() {
        List<ChatUsersLinksDto> listDto = new ArrayList<>();
        List<ChatUsersLinks> list = chatUsersLinksRepository.findAll();
        for (ChatUsersLinks entity: list) {
            listDto.add(chatUsersLinksConverter.dtoFromChatUsersLinks(entity));
        }
        return listDto;
    }

    @Override
    public List<ChatsDto> getChatsByUserId(Long id) {
        List<ChatInfo> chats = chatUsersLinksRepository.getChatsByUserId(id);
        return chats.stream().map(chatsConverter::dtoFromChatInfo).collect(Collectors.toList());
    }

    @Override
    public List<UsersDto> getUsersByChatId(Long id) {
        List<UserInfo> users = chatUsersLinksRepository.getUsersByChatId(id);
        return users.stream().map(usersConverter::dtoFromUserInfo).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteAllByChatId(Long chatId) {
        chatUsersLinksRepository.deleteAllByChatId(chatId);
    }
}