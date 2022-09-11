package com.itea.messenger.service;

import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.exception.ValidationException;
import java.util.List;

public interface ChatUsersLinksService {
    ChatUsersLinksDto saveChatUsersLink(ChatUsersLinks chatUsersLinks);
    ChatUsersLinksDto findById(Long id) throws ValidationException;
    List<ChatUsersLinksDto> findAll();
    List<ChatsDto> getChatsByUserId(Long id);
    List<UsersDto> getUsersByChatId(Long id);
    void deleteAllByChatId(Long chatId);
}
