package com.itea.messenger.interfaces;

import com.itea.messenger.entity.Files;

public interface UsersInfo {
    Long getId();
    String getName();
    String getPassword();
    String getLogin();
    Files getAvatar();
    int getAge();
}
