package com.itea.messenger.controller;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.service.MessagesService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/messages")
@AllArgsConstructor
@Log
public class MessagesController {
    private final MessagesService messagesService;

    @SneakyThrows
    @PostMapping
    public MessagesDto saveMessages(@RequestBody MessagesDto messageDto) {
        log.info("Handling save message: " + messageDto);
        return messagesService.saveMessage(messageDto);
    }

    @GetMapping("{id}")
    public MessagesDto getMessageById(@PathVariable("id") Long messageId) {
        log.info("Handling get message by ID: " + messageId);
        return messagesService.getMessageById(messageId);
    }

    @GetMapping("{chatId}/chat")
    public List<MessagesDto> getAllMessagesByChatId(@PathVariable("chatId") Long chatId) {
        log.info("Handling get all messages by chat ID: " + chatId);
        return messagesService.getAllMessagesByChatId(chatId);
    }

    @GetMapping("{chatId}/chat/{userId}")
    public List<MessagesDto> getMessagesForUserByChatId(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) {
        log.info("Handling get messages by chatId: " + chatId + ",   userId: " + userId);
        return messagesService.getMessagesForUserByChatId(chatId, userId);
    }

    @SneakyThrows
    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Long messageId) {
        log.info("Handling delete message by ID: " + messageId);
        messagesService.deleteMessage(messageId);
    }

}