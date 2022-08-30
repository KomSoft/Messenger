package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.exception.ValidationException;

import java.util.List;

public interface MessagesService {

    MessagesDto saveMessage(MessagesDto messageDto) throws ValidationException;

    MessagesDto getMessageById(Long messageId);

    List<MessagesDto> getAllMessagesByChatId(Long chatId);

    List<MessagesDto> getMessagesForUserByChatId(Long chatId, Long userId);

    void deleteMessage(Long messageId) throws ValidationException;

    void deleteMessage(Long messageId, Long userId);

//    TODO - create multi-table query (clipboard16)
/*
    @Query("SELECT mes.id AS id, mes.chat_id AS chatId, mes.user_id AS userId, mes.message_text AS messageText, mes.file_id AS fileId, mes.date_time AS dateTime, " +
//          mes.StatusLinks     private List<StatusLinks> messageStatus;
            "u.name AS name, from Message mes, Users u WHERE mes.chatId=?1 AND mes.userId=u.id ORDER BY AND mes.dateTime ")
*/
    }
