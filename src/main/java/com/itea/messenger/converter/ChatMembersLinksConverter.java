package com.itea.messenger.converter;

import com.itea.messenger.dto.ChatMembersLinksDto;
import com.itea.messenger.entity.ChatMembersLinks;
import org.springframework.stereotype.Component;

@Component
public class ChatMembersLinksConverter {
    public ChatMembersLinks chatMembersLinkEntityFromDto(ChatMembersLinksDto chatMembersLinksDto) {
        ChatMembersLinks chatMembersLinks = new ChatMembersLinks();
        chatMembersLinks.setId(chatMembersLinksDto.getId());
        chatMembersLinks.setChatId(chatMembersLinksDto.getChatId());
        chatMembersLinks.setUserId(chatMembersLinksDto.getChatId());
        return chatMembersLinks;
    }
    public ChatMembersLinksDto dtoFromChatMembersLinkEntity(ChatMembersLinks chatMembersLinks){
        ChatMembersLinksDto chatMembersLinksDto = new ChatMembersLinksDto();
        chatMembersLinksDto.setId(chatMembersLinks.getId());
        chatMembersLinksDto.setChatId(chatMembersLinks.getChatId());
        chatMembersLinksDto.setUserId(chatMembersLinks.getUserId());
        return chatMembersLinksDto;
    }
}
