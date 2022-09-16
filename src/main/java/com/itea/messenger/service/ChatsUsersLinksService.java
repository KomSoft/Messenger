package com.itea.messenger.service;

import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.dto.ChatsShortDto;
import com.itea.messenger.dto.UsersShortDto;
import com.itea.messenger.entity.ChatsUsersLinks;
import com.itea.messenger.exception.NotFoundException;

import java.util.List;

public interface ChatsUsersLinksService {
    ChatUsersLinksDto saveChatUsersLink(ChatsUsersLinks chatUsersLinks);
    ChatUsersLinksDto findById(Long id) throws NotFoundException;
    List<ChatUsersLinksDto> findAll() throws NotFoundException;
    List<ChatsShortDto> getChatsByUserId(Long id) throws NotFoundException;
    List<UsersShortDto> getUsersByChatId(Long id) throws NotFoundException;
    void deleteAllByChatId(Long chatId);
}
