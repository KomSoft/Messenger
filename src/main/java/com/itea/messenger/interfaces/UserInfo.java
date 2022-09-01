package com.itea.messenger.interfaces;

import com.itea.messenger.type.ChatTypeEnum;

import javax.persistence.Column;

public interface UserInfo {
    long getId();
    String getName();
    String getPassword();
    String getLogin();
    long getPhotoId();
/*
    default long getPhotoId() {
       if (photoId. == null)
    }
*/
    int getAge();
}
