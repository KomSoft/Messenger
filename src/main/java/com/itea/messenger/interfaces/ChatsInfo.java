package com.itea.messenger.interfaces;

import com.itea.messenger.entity.Users;
import com.itea.messenger.type.ChatTypeEnum;
import java.util.Set;

public interface ChatsInfo {
    Long getId();
    String getName();
    String getDescription();
    ChatTypeEnum getChatType();
    Set<Users> getUsers();
}
