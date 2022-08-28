package com.itea.messenger.interfaces;

import com.itea.messenger.entity.StatusLinks;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageByUser {
    Long getId();
    Long getChatId();
    Long getUserId();
    String getMessageText();
    Long getFileId();
    LocalDateTime getDateTime();
    List<StatusLinks> getMessageStatus();

}
