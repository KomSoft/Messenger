package com.itea.messenger.service;

import com.itea.messenger.converter.FilesConverter;
import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.*;
import com.itea.messenger.converter.MessagesConverter;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.interfaces.UsersInfo;
import com.itea.messenger.repository.*;
import com.itea.messenger.type.MessageStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultMessagesService implements MessagesService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DELETED_MESSAGE_TEXT = "Message was deleted";
    @Autowired
    private MessagesConverter messagesConverter;
    @Autowired
    private FilesConverter filesConverter;
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private ChatsUsersLinksRepository chatsUsersLinksRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private StatusLinksRepository statusLinksRepository;
    @Autowired
    private DefaultStatusLinksService defaultStatusLinksService;

/*
        * При створенні повідомлення в чаті з ChatMemberLinks отримується список всіх користувачів чату.
    На його основі (кількості користувачів) по Messages.ID створюються StatusLinks зі статусом SENT
    для кожного користувача (StatusLinks.UserID = ChatMembers.Links.UserID).
    Для відправника статус встановлюється READ.
*/
    @Override
    @Transactional
    public MessagesDto saveMessage(MessagesDto messageDto) throws ValidationException, NotFoundException {
        Messages message = messagesConverter.messagesFromDto(messageDto);
        if (messageDto.getFileName() != null && !messageDto.getFileName().isEmpty()) {
            Files attachment = new Files(messageDto.getFileName());
            filesRepository.save(attachment);
            message.setAttachment(attachment);
        }
        List<UsersInfo> chatUsersList = chatsUsersLinksRepository.getUsersByChatId(message.getChatId());
//        List<UsersInfo> chatUsersList = chatsUsersLinksRepository.getUsersByChatId(message.getChat().getId());
        List<StatusLinks> statusesList = new ArrayList<>();
        for (UsersInfo userInfo : chatUsersList) {
            StatusLinks status = new StatusLinks(userInfo.getId(), MessageStatus.SENT);
//            if (message.getUser().getId().equals(userInfo.getId())) {
            if (message.getUserId().equals(userInfo.getId())) {
                status.setStatus(MessageStatus.READ);
            }
            statusesList.add(status);
        }
        message.setMessageStatuses(statusesList);
//        TODO - this time we get status.messageId == null.
//         Even I get saved message - messagesRepository.findById(newMessage.getId())
//         Why? In the DB all Ok. Cache?
        return messagesConverter.messagesToDto(messagesRepository.save(message));
/*
        Messages newMessage = messagesRepository.save(message);
        return messagesConverter.messagesToDto(messagesRepository.findById(newMessage.getId())
                .orElseThrow(() -> new NotFoundException("Error saving message " + messageDto)));
*/
    }

    @Override
    public MessagesDto getById(Long messageId) throws NotFoundException {
        Messages message = messagesRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("No Message id:" + messageId + " found"));
        return messagesConverter.messagesToDto(message);
    }

    @Override
    public List<MessagesDto> getAllMessagesByChatId(Long chatId) throws NotFoundException {
        List<Messages> messagesList = messagesRepository.findAllMessagesByChatId(chatId);
        if (messagesList.isEmpty()) {
            throw new NotFoundException("No Messages for Chat id:" + chatId + " found");
        }
        return messagesList.stream().map(messagesConverter::messagesToDto).collect(Collectors.toList());
    }

/*
    Коли користувач видаляє повідомлення –
    а) Якщо це власник (Messages.UserID) - то перший раз текст повідомлення міняється (наприклад)
    на "Message was deleted" (String final static). Так його побачать всі користувачі.
    Якщо це вдруге (текст вже "Message was deleted") - статус цього повідомлення (StatusLinks.UserID)
    міняється на DELETED.
    б) Якщо це інший користувач, то статус цього повідомлення для цього користувача (StatusLinks.UserID)
    міняється на DELETED незалежно від тексту. Відповідно, повідомлення зі статусом DELETED не висвітлюються
    в чаті для конкретного користувача.
    в) якщо статуси для всіх користувачів = DELETED, тоді повністю видаляємо повідомлення з репозіторію
*/
    @Override
    public void deleteMessage(Long messageId, Long userId) throws NotFoundException {
        Messages message = messagesRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Message id:{0} not found", messageId)));
//      Author deletes original message
        if (message.getUserId().equals(userId)) {
//        if (message.getUser().getId().equals(userId)) {
//            if already "DELETED_MESSAGE_TEXT" - set DELETE status for Author
            if (message.getMessageText().equalsIgnoreCase(DELETED_MESSAGE_TEXT)) {
                for (int i = 0; i < message.getMessageStatuses().size(); i++) {
                    if (message.getMessageStatuses().get(i).getUserId().equals(userId)) {
                        try {
                            message.getMessageStatuses().get(i).setStatus(MessageStatus.DELETED);
                            defaultStatusLinksService.saveStatusById(message.getMessageStatuses().get(i).getId(), MessageStatus.DELETED);
                        } catch (NotFoundException e) {
//                          not critical
                            log.error(MessageFormat.format("{0} (Message id:{1}, User id:{2})", e.getMessage(), messageId, userId));
                        }
                    }
                }
            } else {
//            if first time - change text only (for all users)
                message.setMessageText(DELETED_MESSAGE_TEXT);
                messagesRepository.save(message);
                return;
            }
        } else {
//      Not Author deletes -
            for (int i = 0; i < message.getMessageStatuses().size(); i++) {
                if (message.getMessageStatuses().get(i).getUserId().equals(userId)) {
                    try {
                        message.getMessageStatuses().get(i).setStatus(MessageStatus.DELETED);
                        defaultStatusLinksService.saveStatusById(message.getMessageStatuses().get(i).getId(), MessageStatus.DELETED);
                    } catch (NotFoundException e) {
//                      not critical
                        log.error(MessageFormat.format("{0} (Message id:{1}, User id:{2})", e.getMessage(), messageId, userId));
                    }
                }
            }
        }
//      Check if we can delete message from repository
        boolean isAllDelete = true;
//      No statuses. Author deletes message. YES
        if (message.getMessageStatuses().size() == 0) {
            isAllDelete = message.getUserId().equals(userId);
//            isAllDelete = message.getUser().getId().equals(userId);
        }
//      Check if all statuses == DELETED
        int i = 0;
        while (isAllDelete && (i < message.getMessageStatuses().size())) {
            isAllDelete = message.getMessageStatuses().get(i).getStatus().equals(MessageStatus.DELETED);
            i++;
        }
        if (isAllDelete) {
//      orphanRemoval = true, we don't need to remove StatusLinks
//            statusLinksRepository.deleteAllByMessageId(messageId);
            messagesRepository.deleteById(messageId);
        }
    }

    @Transactional
    @Override
    public void deleteAllByChatId(Long chatId) throws EmptyResultDataAccessException {
//      orphanRemoval = true, we don't need to remove StatusLinks
//        statusLinksRepository.deleteAllByMessageId(messageId);
        messagesRepository.deleteAllByChatId(chatId);
    }

    @Override
    public void deleteAllByChatIdAndUserId(Long chatId, Long userId) {
//      orphanRemoval = true, we don't need to remove StatusLinks
        messagesRepository.deleteAllByChatIdAndUserId(chatId, userId);
    }

/*
   1.	При висвітлюванні повідомлень з чату для користувача висвітлюються лише ті повідомлення,
   які створені після його під’єднання до чату та не мають статусу DELETED.
   Це потребує додавання в ChatUsersLinks поля JoinDate. Воно заповнюється один раз, коли користувач
   під’єднується до чату.
*/
    @Override
    public List<MessagesDto> getMessagesForUserByChatId(Long chatId, Long userId) throws NotFoundException {
        ChatsUsersLinks chatUser = chatsUsersLinksRepository.findByChatIdAndUserId(chatId, userId);
        if (chatUser == null) {
           throw new NotFoundException(MessageFormat.format("User id:{0} isn't member of Chat id:{1}", userId, chatId));
        }
        List<Messages> messagesList = messagesRepository.findMessagesByChatIdAndDateTimeAfter(chatId, chatUser.getJoinDate());
        List<MessagesDto> resultList = new ArrayList<>();
        //        can't use predicate because some messages don't have status ( -> Exception)
//        Predicate<Long> notDeleted = userIdForStatus -> !defaultStatusLinksService.findById(userIdForStatus).getStatus().equals(MessageStatus.DELETED);
        for (Messages message : messagesList) {
            MessageStatus status = message.getStatusByUserId(userId);
            if (status == null || status != MessageStatus.DELETED) {
                resultList.add(messagesConverter.messagesToDto(message));
            }
        }
        return resultList;
    }

/*
        * Коли власник повідомлення (Messages.UserID, інші не мають права редагувати)
    його редагує – перебираємо (StatusLinks.MessageID = Messages.ID) та міняємо статус READ на EDITED.
    Інші статуси можна не міняти, бо якщо користувач не отримав або не прочитав (SENT або DELIVERED) повідомлення,
    то він і не буде знати, що його редагували. (Тут трішки коряво, але інакше треба додавати окреме поле
    для EDITED (true/false), щоб не плутатись в статусах).
    }
*/
    @Override
    @Transactional
    public MessagesDto editMessage(MessagesDto messageDto, Long userId) throws ValidationException, NotFoundException {
        Messages editedMessage = messagesConverter.messagesFromDto(messageDto);
        if (editedMessage.getId() == null) {
            throw new ValidationException("Incorrect message id");
        }
        ChatsUsersLinks chat_user = chatsUsersLinksRepository.findByChatIdAndUserId(editedMessage.getChatId(), editedMessage.getUserId());
//        ChatsUsersLinks chat_user = chatsUsersLinksRepository.findByChatIdAndUserId(editedMessage.getChat().getId(), editedMessage.getUser().getId());
        if (chat_user == null) {
            throw new ValidationException("User id:" + editedMessage.getUserId() + " isn't member of Chat id:" + editedMessage.getChatId());
//            throw new ValidationException("User id:" + editedMessage.getUser().getId() + " isn't member of Chat id:" + editedMessage.getChat().getId());
        }
        Messages newMessage = messagesRepository.findById(editedMessage.getId())
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Message id:{0} for edit not found", editedMessage.getId())));
        if (!newMessage.getUserId().equals(userId)) {
//        if (!newMessage.getUser().getId().equals(userId)) {
            throw new ValidationException("Only Author can edit message");
        }
        newMessage.setMessageText(editedMessage.getMessageText() == null ? "" : editedMessage.getMessageText());
        if (editedMessage.getFile() == null) {
            newMessage.removeAttachment(newMessage.getFile());
        } else {
            Files newFile = filesConverter.fileEntityFromDto(new FilesDto(messageDto.getFileId(), messageDto.getFileName()));
            Files oldFile = editedMessage.getFile();
            editedMessage.removeAttachment(oldFile);
            Files newAttachment = filesRepository.save(newFile);
            newMessage.setAttachment(newAttachment);
        }
        List<StatusLinks> statusList = new ArrayList<>(newMessage.getMessageStatuses());
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).getStatus() == MessageStatus.READ) {
                newMessage.removeStatus(statusList.get(i));
                statusList.get(i).setStatus(MessageStatus.EDITED);
                newMessage.addStatus(statusList.get(i));
            }
        }
        return messagesConverter.messagesToDto(messagesRepository.save(newMessage));
    }

}
