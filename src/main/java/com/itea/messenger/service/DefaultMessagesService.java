package com.itea.messenger.service;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.Messages;
import com.itea.messenger.converter.MessagesConverter;
import com.itea.messenger.repository.MessagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultMessagesService implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final MessagesConverter messagesConverter;
    private static final String DELETED_MESSAGE_TEXT = "Message was deleted";

    @Override
    public MessagesDto saveMessage(MessagesDto messageDto) {
//        validateMessagesDto(messageDto);
        Messages savedMessage = messagesRepository.save(messagesConverter.messagesFromDto(messageDto));
        return messagesConverter.messagesToDto(savedMessage);
    }


/*
    private void validateMessagesDto(MessagesDto messageDto) {
//        check if message length <= 255.
        if (messageDto.getMessageText().length() > 255) {
            messageDto.setMessageText(messageDto.getMessageText().substring(0, 254));
        }
    }
*/
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

    @Override
    public List<MessagesDto> getAllMessagesByChatIdByStartDateAfter(Long chatId, LocalDateTime dateTime) {
        return messagesRepository.getAllMessagesByChatIdByStartDateAfter(chatId, dateTime).stream()
                .map(messagesConverter::messagesToDto).collect(Collectors.toList());
    }

    @Overrid
    public void deleteMessage(Long messageId) {
//     First time change message text to DELETED_MESSAGE_TEXT = "Message was deleted"
//     Next time - delete message
//        TODO extend logic
        Optional<Messages> message = messagesRepository.findById(messageId);
       if (message.isPresent()) {
           if (message.get().getMessageText().equalsIgnoreCase(DELETED_MESSAGE_TEXT)) {
               messagesRepository.deleteById(messageId);
           } else {
               message.get().setMessageText(DELETED_MESSAGE_TEXT);
               messagesRepository.save(message.get());
           }
      }
    }
}
