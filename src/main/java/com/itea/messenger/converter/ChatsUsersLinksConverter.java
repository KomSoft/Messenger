package com.itea.messenger.converter;

import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.entity.ChatsUsersLinks;
import org.springframework.stereotype.Component;

@Component
public class ChatsUsersLinksConverter {

    public ChatUsersLinksDto chatUsersLinksEntityToDto(ChatsUsersLinks chatUsersLinks){

        ChatUsersLinksDto chatUsersLinksDto = new ChatUsersLinksDto();
        chatUsersLinksDto.setId(chatUsersLinks.getId());
        chatUsersLinksDto.setChatId(chatUsersLinks.getChatId());
        chatUsersLinksDto.setUserId(chatUsersLinks.getUserId());
        chatUsersLinksDto.setJoinDate(chatUsersLinks.getJoinDate());
        chatUsersLinksDto.setViewDate(chatUsersLinks.getViewDate());
        return chatUsersLinksDto;
    }
}
