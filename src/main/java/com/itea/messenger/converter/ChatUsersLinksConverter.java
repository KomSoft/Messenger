package com.itea.messenger.converter;

import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.entity.ChatUsersLinks;
import org.springframework.stereotype.Component;

@Component
public class ChatUsersLinksConverter {
    public ChatUsersLinks chatUsersLinksFromDto(ChatUsersLinksDto chatUsersLinksDto) {
        ChatUsersLinks chatUsersLinks = new ChatUsersLinks();
        chatUsersLinks.setId(chatUsersLinksDto.getId());
        chatUsersLinks.setChatId(chatUsersLinksDto.getChatId());
        chatUsersLinks.setUserId(chatUsersLinksDto.getChatId());
        return chatUsersLinks;
    }
    public ChatUsersLinksDto dtoFromChatUsersLinks(ChatUsersLinks chatUsersLinks){
        ChatUsersLinksDto chatUsersLinksDto = new ChatUsersLinksDto();
        chatUsersLinksDto.setId(chatUsersLinks.getId());
        chatUsersLinksDto.setChatId(chatUsersLinks.getChatId());
        chatUsersLinksDto.setUserId(chatUsersLinks.getUserId());
        return chatUsersLinksDto;
    }
}
