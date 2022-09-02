package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.entity.Messages;
import com.itea.messenger.converter.MessagesConverter;
import com.itea.messenger.entity.StatusLinks;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.interfaces.UserInfo;
import com.itea.messenger.repository.ChatUsersLinksRepository;
import com.itea.messenger.repository.MessagesRepository;
import com.itea.messenger.type.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.Objects.isNull;

@Log
@Service
@AllArgsConstructor
public class DefaultMessagesService implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final MessagesConverter messagesConverter;
    private final ChatUsersLinksRepository chatUsersLinksRepository;
    private final DefaultStatusLinksService defaultStatusLinksService;
    private static final String DELETED_MESSAGE_TEXT = "Message was deleted";

    private void validateMessagesDto(MessagesDto messageDto) throws ValidationException {
        if (isNull(messageDto)) {
            throw new ValidationException("Object MessageDto is null");
        }
        if (messageDto.getUserId() == null) {
            throw new ValidationException("[MessageDto].userId is null");
        }
        if (messageDto.getChatId() == null) {
            throw new ValidationException("[MessageDto].chatId is null");
        }
        if ((messageDto.getMessageText() == null || messageDto.getMessageText().isEmpty()) && messageDto.getFileId() == null) {
            throw new ValidationException("[MessageDto].message can be empty only if file is attached");
        }
        if (messageDto.getMessageText().length() > 255) {
            messageDto.setMessageText(messageDto.getMessageText().substring(0, 254));
//        TODO - should we check if messageStatus list != null when read?
        }
    }

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
        validateMessagesDto(messageDto);
        Messages message = messagesConverter.messagesFromDto(messageDto);
        Messages savedMessage;
//        save new message
        if (!messagesRepository.findById(message.getId()).isPresent()) {
            List<UserInfo> chatUsersList = chatUsersLinksRepository.getUsersByChatId(message.getChatId());
            List<StatusLinks> statusLinksList = new ArrayList<>();
            StatusLinks status = new StatusLinks();
//  TODO - save the statusLinks before save message
/*
            error: org.hibernate.TransientObjectException: object references an unsaved transient instance -
            save the transient instance before flushing: com.itea.messenger.entity.StatusLinks
*/
            for (UserInfo user : chatUsersList) {
                status.setUserId(user.getId());
                status.setStatus(MessageStatus.SENT);
                if (message.getUserId().equals(user.getId())) {
                    status.setStatus(MessageStatus.READ);
                }
                statusLinksList.add(status);
            }
            message.setMessageStatus(statusLinksList);
        } else {
//            update (edit) existing message, change statuses to EDITED
            for (int i = 0; i < message.getMessageStatus().size(); i++) {
                if (message.getMessageStatus().get(i).getStatus().equals(MessageStatus.READ)) {
                    message.getMessageStatus().get(i).setStatus(MessageStatus.EDITED);
                }
            }
        }
        savedMessage = messagesRepository.save(message);
        return messagesConverter.messagesToDto(savedMessage);
    }

    @Override
    public MessagesDto getMessageById(Long messageId) {
        Optional<Messages> message = messagesRepository.findById(messageId);
        return message.map(messagesConverter::messagesToDto).orElse(null);
    }

    @Override
    public List<MessagesDto> getAllMessagesByChatId(Long chatId) {
        return messagesRepository.findAllMessagesByChatId(chatId).stream().map(messagesConverter::messagesToDto)
                .collect(Collectors.toList());
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
    в) якщо статуси для всіх користувачів = DELETED, тоді повністю видяляємо повідомлення з репозіторію
 */
    @Override
    public void deleteMessage(Long messageId) throws ValidationException {
        throw new ValidationException("Method 'DeleteMessage(Long messageId)' not exists. Please change to 'DeleteMessage(Long messageId, Long userId)'");
    }

    @Override
    public void deleteMessage(Long messageId, Long userId) {
        Optional<Messages> optionalMessage = messagesRepository.findById(messageId);
        if (!optionalMessage.isPresent()) {
           return;
        }
        Messages message = optionalMessage.get();
        if (message.getUserId().equals(userId)) {
            if (message.getMessageText().equalsIgnoreCase(DELETED_MESSAGE_TEXT)) {
                for (int i = 0; i < message.getMessageStatus().size(); i++) {
                    if (message.getMessageStatus().get(i).getUserId().equals(userId)) {
                        message.getMessageStatus().get(i).setStatus(MessageStatus.DELETED);
                    }
                }
            } else {
                message.setMessageText(DELETED_MESSAGE_TEXT);
               messagesRepository.save(message);
           }
        } else {
            for (int i = 0; i < message.getMessageStatus().size(); i++) {
                if (message.getMessageStatus().get(i).getUserId().equals(userId)) {
                    message.getMessageStatus().get(i).setStatus(MessageStatus.DELETED);
                }
            }
        }
        boolean isAllDelete = true;
        if (message.getMessageStatus().size() == 0) {
            isAllDelete = message.getUserId().equals(userId);
        }
        int i = 0;
        while (isAllDelete && (i < message.getMessageStatus().size())) {
            isAllDelete = message.getMessageStatus().get(i).getStatus().equals(MessageStatus.DELETED);
            i++;
        }
        if (isAllDelete) {
//          TODO -   should we delete Statuses from StatusLinks?
            messagesRepository.deleteById(messageId);
        }
    }

/*
    1.	При висвітлюванні повідомлень з чату для користувача висвітлюються лише ті повідомлення,
    які створені після його під’єднання до чату та не мають статусу DELETED.
    Це потребує додавання в ChatUsersLinks поля JoinDate. Воно заповнюється один раз, коли користувач
    під’єднується до чату.
*/
    @Override
    public List<MessagesDto> getMessagesForUserByChatId(Long chatId, Long userId) {
        ChatUsersLinks chatUser = chatUsersLinksRepository.findByChatIdAndUserId(chatId, userId);
        List<Messages> messagesList = messagesRepository.findMessagesByChatIdAndDateTimeAfter(chatId, chatUser.getJoinDate());
        List<MessagesDto> result = new ArrayList<>();
        //        can use predicate if no exception
//        Predicate<Long> notDeleted = userIdForStatus -> !defaultStatusLinksService.findById(userIdForStatus).getStatus().equals(MessageStatus.DELETED);
        for (Messages message : messagesList) {
            MessageStatus status = message.getStatusByUserId(userId);
            if (status != null && status != MessageStatus.DELETED) {
                result.add(messagesConverter.messagesToDto(message));
            } else {
//                skip (or need to do something, throw exception?)
            }
        }
        return result;
    }

}
