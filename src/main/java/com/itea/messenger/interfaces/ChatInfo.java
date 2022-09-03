package com.itea.messenger.interfaces;

import com.itea.messenger.type.ChatTypeEnum;

public interface ChatInfo {
    long getId();
    String getName();
    String getDescription();
    ChatTypeEnum getChatType();
}
