package com.itea.messenger.converter;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.*;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.ChatsRepository;
import com.itea.messenger.repository.FilesRepository;
import com.itea.messenger.repository.StatusLinksRepository;
import com.itea.messenger.repository.UsersRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class MessagesConverter {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private ChatsRepository chatsRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private  UsersRepository usersRepository;
    @Autowired
    private StatusLinksRepository statusLinksRepository;
    @Autowired
    private StatusLinksConverter statusLinksConverter;

    private void validateMessagesDto(MessagesDto messageDto) throws ValidationException {
        if (isNull(messageDto)) {
            throw new ValidationException("[MessageDto] object is null");
        }
        if (messageDto.getUserId() == null) {
            throw new ValidationException("[MessageDto] userId is null");
        }
        if (messageDto.getChatId() == null) {
            throw new ValidationException("[MessageDto] chatId is null");
        }
        if ((messageDto.getMessageText() == null || messageDto.getMessageText().isEmpty()) && messageDto.getFileId() == null) {
            throw new ValidationException("[MessageDto] messageText can be empty only if file is attached");
        }
        if (messageDto.getMessageText().length() > 255) {
            messageDto.setMessageText(messageDto.getMessageText().substring(0, 254));
        }
    }

    public MessagesDto messagesToDto(Messages message) {
        MessagesDto messageDto = new MessagesDto();
        messageDto.setId(message.getId());
        messageDto.setChatId(message.getChatId());
//        messageDto.setChatId(message.getChat().getId());
//        messageDto.setChatName(message.getChat().getName());
        messageDto.setUserId(message.getUserId());
//        messageDto.setUserId(message.getUser().getId());
//        messageDto.setUserName(message.getUser().getName());
        messageDto.setMessageText(message.getMessageText());
        if (message.getFile() != null) {
            messageDto.setFileId(message.getFile().getId());
            messageDto.setFileName(message.getFile().getFileName());
            messageDto.setFileType(message.getFile().getFileType());
        }
        messageDto.setDateTime(message.getDateTime());
//        in normal case message must have at least one status (for owner) therefore we don't check isNull
        if (message.getMessageStatuses() != null && message.getMessageStatuses().size() > 0) {
            messageDto.setStatuses(message.getMessageStatuses().stream().map(statusLinksConverter::statusLinksToDto).collect(Collectors.toList()));
        }
        return messageDto;
    }

    public Messages messagesFromDto(MessagesDto messageDto) throws ValidationException {
        validateMessagesDto(messageDto);
        Chats chat = chatsRepository.findById(messageDto.getChatId())
                .orElseThrow(() -> new ValidationException("[MessageDto] Chat id:" + messageDto.getChatId() + " not found"));
        Users user = usersRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new ValidationException("[MessageDto] User id:" + messageDto.getUserId() + " not found"));
        Messages message = new Messages();
        message.setId(messageDto.getId());
        message.setUserId(messageDto.getUserId());
        message.setChatId(messageDto.getChatId());
//        message.setChat(chat);
//        message.setUser(user);
        message.setMessageText(messageDto.getMessageText());
        if (messageDto.getFileId() == null) {
            message.setFile(null);
        } else {    // not critical
            message.setFile(filesRepository.findById(messageDto.getFileId()).orElse(null));
        }
        message.setDateTime(messageDto.getDateTime());
//      ignore messageDto statuses and get them from repository if exists. For new message they will create after
        List<StatusLinks> statuses = statusLinksRepository.findByMessageId(messageDto.getId());
        if (statuses == null || statuses.isEmpty()) {
            message.setMessageStatuses(null);
        } else {
            message.setMessageStatuses(statuses);
        }
        return message;
    }
}
