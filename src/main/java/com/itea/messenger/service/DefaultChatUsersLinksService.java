package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.converter.ChatUsersLinksConverter;
import com.itea.messenger.converter.UsersConverter;
import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.ChatUsersLinksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultChatUsersLinksService implements ChatUsersLinksService {
    private final ChatUsersLinksRepository chatUsersLinksRepository;
    private final ChatUsersLinksConverter chatMembersLinksConverter;
    private final UsersConverter usersConverter;
    private final ChatsConverter chatsConverter;

    private void validateChatMembersLinkDto(ChatUsersLinksDto chatMembersLinksDto)throws ValidationException {
        if (isNull(chatMembersLinksDto)) {
            throw new ValidationException("Object chat members link is null");
        }
        if (isNull(chatMembersLinksDto.getChatId()) || isNull(chatMembersLinksDto.getUserId())) {
            throw new ValidationException("Chat id or user id is null");
        }
    }
    @Override
    public ChatUsersLinksDto saveChatMembersLink(ChatUsersLinksDto chatMembersLinksDto) throws ValidationException {
        validateChatMembersLinkDto(chatMembersLinksDto);
        ChatUsersLinks chatMembersLinks = chatUsersLinksRepository.save(chatMembersLinksConverter.chatUsersLinksFromDto(chatMembersLinksDto));
        return chatMembersLinksConverter.dtoFromChatUsersLinks(chatMembersLinks);
    }

    @Override
    public ChatUsersLinksDto findById(Long id) throws ValidationException {
        ChatUsersLinks chatMembersLinks = chatUsersLinksRepository.findById(id).orElseThrow(() ->new ValidationException("No object with this id"));
        return chatMembersLinksConverter.dtoFromChatUsersLinks(chatMembersLinks);
    }

    @Override
    public List<ChatUsersLinksDto> findAll() {
        List<ChatUsersLinksDto> listDto = new ArrayList<>();
        List<ChatUsersLinks> list = chatUsersLinksRepository.findAll();
        for (ChatUsersLinks entity: list) {
            listDto.add(chatMembersLinksConverter.dtoFromChatUsersLinks(entity));
        }
        return listDto;
    }

    @Override
    public List<ChatsDto> getChatsByUserId(Long id) {
        List<Chats> list = chatUsersLinksRepository.getChatsByUserId(id);
        return list.stream().map(chatsConverter::dtoFromChatEntity).collect(Collectors.toList());
    }

    @Override
    public List<UsersDto> getUsersByChatId(Long id) {
        List<Users> list = chatUsersLinksRepository.getUsersByChatId(id);
        return list.stream().map(usersConverter::dtoFromUsersEntity).collect(Collectors.toList());
    }
}