package com.itea.messenger.service;

import com.itea.messenger.dto.ChatMembersLinksDto;

import java.util.List;

public interface ChatMembersLinksService {
    ChatMembersLinksDto saveChatMembersLink(ChatMembersLinksDto chatMembersLinksDto)throws ValidationException;
    ChatMembersLinksDto findById(Long id)throws ValidationException;
    List<ChatMembersLinksDto> findAll();
}
