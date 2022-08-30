package com.itea.messenger.service;

import com.itea.messenger.converter.ChatMembersLinksConverter;
import com.itea.messenger.dto.ChatMembersLinksDto;
import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.repository.ChatMembersLinksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultChatMembersLinksService implements ChatMembersLinksService {
    private final ChatMembersLinksRepository chatMembersLinksRepository;
    private final ChatMembersLinksConverter chatMembersLinksConverter;

    private void validateChatMembersLinkDto(ChatMembersLinksDto chatMembersLinksDto)throws ValidationException {
        if (isNull(chatMembersLinksDto)) {
            throw new ValidationException("Object chat members link is null");
        }
        if (isNull(chatMembersLinksDto.getChatId()) || isNull(chatMembersLinksDto.getUserId())) {
            throw new ValidationException("Chat id or user id is null");
        }
    }
    @Override
    public ChatMembersLinksDto saveChatMembersLink(ChatMembersLinksDto chatMembersLinksDto) throws ValidationException {
        validateChatMembersLinkDto(chatMembersLinksDto);
        ChatUsersLinks chatMembersLinks = chatMembersLinksRepository.save(chatMembersLinksConverter.chatMembersLinkEntityFromDto(chatMembersLinksDto));
        return chatMembersLinksConverter.dtoFromChatMembersLinkEntity(chatMembersLinks);
    }

    @Override
    public ChatMembersLinksDto findById(Long id) throws ValidationException {
        ChatUsersLinks chatMembersLinks = chatMembersLinksRepository.findById(id).orElseThrow(() ->new ValidationException("No object with this id"));
        return chatMembersLinksConverter.dtoFromChatMembersLinkEntity(chatMembersLinks);
    }

    @Override
    public List<ChatMembersLinksDto> findAll() {
        List<ChatMembersLinksDto> listDto = new ArrayList<>();
        List<ChatUsersLinks> list = chatMembersLinksRepository.findAll();
        for (ChatUsersLinks entity: list
             ) {
            listDto.add(chatMembersLinksConverter.dtoFromChatMembersLinkEntity(entity));

        }
        return listDto;
    }
}