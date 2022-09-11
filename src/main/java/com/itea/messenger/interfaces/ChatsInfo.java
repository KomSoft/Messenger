package com.itea.messenger.interfaces;

import com.itea.messenger.type.ChatTypeEnum;

public interface ChatsInfo {
    Long getId();
    String getName();
    String getDescription();
    ChatTypeEnum getChatType();
}
