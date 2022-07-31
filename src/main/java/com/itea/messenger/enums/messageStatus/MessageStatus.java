package com.itea.messenger.enums.messageStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageStatus {
    READ,
    UNREAD,
    SENT,
    DELIVERED,
    DELETED,
    EDITED
}
