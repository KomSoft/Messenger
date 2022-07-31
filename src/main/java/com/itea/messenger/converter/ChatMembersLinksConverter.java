package com.itea.messenger.converter;

import com.itea.messenger.dto.ChatMembersLinksDto;
import com.itea.messenger.entity.ChatMembersLinks;
import org.springframework.stereotype.Component;

@Component
public class ChatMembersLinksConverter {
    public ChatMembersLinks chatMembersLinkEntityFromDto(ChatMembersLinksDto chatMembersLinksDto) {
        ChatMembersLinks chatMembersLinks = new ChatMembersLinks();
        chatMembersLinks.setId(chatMembersLinksDto.getId());
        chatMembersLinks.setChatID(chatMembersLinksDto.getChatID());
        chatMembersLinks.setUserID(chatMembersLinksDto.getChatID());
        return chatMembersLinks;
    }
    public ChatMembersLinksDto dtoFromChatMembersLinkEntity(ChatMembersLinks chatMembersLinks){
        ChatMembersLinksDto chatMembersLinksDto = new ChatMembersLinksDto();
        chatMembersLinksDto.setId(chatMembersLinks.getId());
        chatMembersLinksDto.setChatID(chatMembersLinks.getChatID());
        chatMembersLinksDto.setUserID(chatMembersLinks.getUserID());
        return chatMembersLinksDto;
    }
}
