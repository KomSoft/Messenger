package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.ChatsUsersLinks;
import com.itea.messenger.entity.Messages;
import com.itea.messenger.converter.MessagesConverter;
import com.itea.messenger.entity.StatusLinks;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.ChatsUsersLinksRepository;
import com.itea.messenger.repository.MessagesRepository;
import com.itea.messenger.repository.StatusLinksRepository;
import com.itea.messenger.type.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultMessagesService implements MessagesService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DELETED_MESSAGE_TEXT = "Message was deleted";
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private MessagesConverter messagesConverter;
    @Autowired
    private ChatsUsersLinksRepository chatUsersLinksRepository;
    @Autowired
    private DefaultStatusLinksService defaultStatusLinksService;
    @Autowired
    private StatusLinksRepository statusLinksRepository;

/*
        * При створенні повідомлення в чаті з ChatMemberLinks отримується список всіх користувачів чату.
    На його основі (кількості користувачів) по Messages.ID створюються StatusLinks зі статусом SENT
    для кожного користувача (StatusLinks.UserID = ChatMembers.Links.UserID).
    Для відправника статус встановлюється READ.
        * (ChatsService, MessagesService) Коли власник повідомлення (Messages.UserID, інші не мають права редагувати)
    його редагує – перебираємо (StatusLinks.MessageID = Messages.ID) та міняємо статус READ на EDITED.
    Інші статуси можна не міняти, бо якщо користувач не отримав або не прочитав (SENT або DELIVERED) повідомлення,
    то він і не буде знати, що його редагували. (Тут трішки коряво, але інакше треба додавати окреме поле
    для EDITED (true/false), щоб не плутатись в статусах).
    }
*/
//    TODO - may be make two method: save and update (for edited message)?
    @Override
    public MessagesDto saveMessage(MessagesDto messageDto) throws ValidationException {
//        validateMessagesDto(messageDto);
        Messages message = messagesConverter.messagesFromDto(messageDto);
        Messages savedMessage;
//        save new message
//        if (!messagesRepository.findById(message.getId()).isPresent()) {
//            List<UserInfo> chatUsersList = chatUsersLinksRepository.getUsersByChatId(message.getChat().getId());
            List<StatusLinks> statusLinksList = new ArrayList<>();
            StatusLinks status = new StatusLinks();
//  TODO - save the statusLinks before save message
/*
            error: org.hibernate.TransientObjectException: object references an unsaved transient instance -
            save the transient instance before flushing: com.itea.messenger.entity.StatusLinks
*/
/*
            for (UserInfo user : chatUsersList) {
                status.setUserId(user.getId());
                status.setStatus(MessageStatus.SENT);
                if (message.getUserId().equals(user.getId())) {
                    status.setStatus(MessageStatus.READ);
                }
                statusLinksList.add(status);
            }
            message.setMessageStatuses(statusLinksList);
*/
//        }
/*
        else {
//            update (edit) existing message, change statuses to EDITED
            for (int i = 0; i < message.getMessageStatus().size(); i++) {
                if (message.getMessageStatus().get(i).getStatus().equals(MessageStatus.READ)) {
                    message.getMessageStatus().get(i).setStatus(MessageStatus.EDITED);
                }
            }
        }
*/
        savedMessage = messagesRepository.save(message);
        return messagesConverter.messagesToDto(savedMessage);
    }

    @Override
    public MessagesDto getById(Long messageId) throws NotFoundException {
        Messages message = messagesRepository.findById(messageId).orElseThrow(() -> new NotFoundException("No message with id:" + messageId));
//        Optional<Messages> message = messagesRepository.findById(messageId);
        return messagesConverter.messagesToDto(message);

/*
        Users user = usersRepository.findById(id).orElseThrow(() -> new ValidationException("No users with this id"));
        return usersConverter.userToDto(user);
*/

    }

    @Override
    public List<MessagesDto> getAllMessagesByChatId(Long chatId) throws NotFoundException {
        List<Messages> messagesList = messagesRepository.findAllMessagesByChatId(chatId);
        if (messagesList.isEmpty()) {
            throw new NotFoundException("No messages for chat id:" + chatId + " found");
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
    public void deleteMessage(Long messageId) throws ValidationException {
        throw new ValidationException("Method 'DeleteMessage(Long messageId)' not exists. Please change to 'DeleteMessage(Long messageId, Long userId)'");
    }

    @Override
    public void deleteMessage(Long messageId, Long userId) throws EmptyResultDataAccessException {
        Optional<Messages> optionalMessage = messagesRepository.findById(messageId);
        if (!optionalMessage.isPresent()) {
           return;
        }
        Messages message = optionalMessage.get();
        if (message.getUser().getId().equals(userId)) {
            if (message.getMessageText().equalsIgnoreCase(DELETED_MESSAGE_TEXT)) {
                for (int i = 0; i < message.getMessageStatuses().size(); i++) {
                    if (message.getMessageStatuses().get(i).getUser().getId().equals(userId)) {
                        message.getMessageStatuses().get(i).setStatus(MessageStatus.DELETED);
                    }
                }
            } else {
                message.setMessageText(DELETED_MESSAGE_TEXT);
               messagesRepository.save(message);
           }
        } else {
            for (int i = 0; i < message.getMessageStatuses().size(); i++) {
                if (message.getMessageStatuses().get(i).getUser().getId().equals(userId)) {
                    message.getMessageStatuses().get(i).setStatus(MessageStatus.DELETED);
                }
            }
        }
        boolean isAllDelete = true;
        if (message.getMessageStatuses().size() == 0) {
            isAllDelete = message.getUser().getId().equals(userId);
        }
        int i = 0;
        while (isAllDelete && (i < message.getMessageStatuses().size())) {
            isAllDelete = message.getMessageStatuses().get(i).getStatus().equals(MessageStatus.DELETED);
            i++;
        }
        if (isAllDelete) {
//          TODO -   should we delete Statuses from StatusLinks?
            statusLinksRepository.deleteAllByMessageId(messageId);
            messagesRepository.deleteById(messageId);
        }
    }

    @Transactional
    @Override
    public void deleteAllMessagesByChatId(Long chatId) throws EmptyResultDataAccessException {
//        statusLinksRepository.deleteAllByMessageId(messageId);
        messagesRepository.deleteAllByChatId(chatId);
    }

    /*
        1.	При висвітлюванні повідомлень з чату для користувача висвітлюються лише ті повідомлення,
        які створені після його під’єднання до чату та не мають статусу DELETED.
        Це потребує додавання в ChatUsersLinks поля JoinDate. Воно заповнюється один раз, коли користувач
        під’єднується до чату.
    */
    @Override
    public List<MessagesDto> getMessagesForUserByChatId(Long chatId, Long userId) {
        ChatsUsersLinks chatUser = chatUsersLinksRepository.findByChatIdAndUserId(chatId, userId);
        List<Messages> messagesList = messagesRepository.findMessagesByChatIdAndDateTimeAfter(chatId, chatUser.getJoinDate());
        List<MessagesDto> result = new ArrayList<>();
        //        can use predicate if no exception
//        Predicate<Long> notDeleted = userIdForStatus -> !defaultStatusLinksService.findById(userIdForStatus).getStatus().equals(MessageStatus.DELETED);
        for (Messages message : messagesList) {
            MessageStatus status = message.getStatusByUserId(userId);
//log.info("[getMessagesForUserByChatId]" + messagesConverter.messagesToDto(message));
//            if (status != null && status != MessageStatus.DELETED) {
            if (status == null || (status != null && status != MessageStatus.DELETED)) {
                result.add(messagesConverter.messagesToDto(message));
            } else {
//                skip (or need to do something, throw exception?)
            }
        }
        return result;
    }

}
