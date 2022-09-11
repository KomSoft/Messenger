package com.itea.messenger.controller;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.service.MessagesService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/messages")
@NoArgsConstructor
@Log
public class MessagesController {

    @Autowired
    MessagesService messagesService;

    @SneakyThrows
    @PostMapping
    public MessagesDto saveMessages(@RequestBody MessagesDto messageDto) {
        log.info("Handling save Message: " + messageDto);
        return messagesService.saveMessage(messageDto);
    }

    @SneakyThrows
    @GetMapping("{id}")
    public MessagesDto getMessageById(@PathVariable("id") Long messageId) {
        log.info("Handling get Message by id:" + messageId);
        return messagesService.getMessageById(messageId);
    }

    @GetMapping("{chatId}/chat")
    public List<MessagesDto> getAllMessagesByChatId(@PathVariable("chatId") Long chatId) {
        log.info("Handling get all messages by chatId:" + chatId);
        return messagesService.getAllMessagesByChatId(chatId);
    }

    @GetMapping("{chatId}/chat/{userId}")
    public List<MessagesDto> getMessagesForUserByChatId(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) {
        log.info("Handling get messages for User id:" + userId + " by chatId:" + chatId);
        return messagesService.getMessagesForUserByChatId(chatId, userId);
    }

    @SneakyThrows
    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Long messageId) {
        log.info("Handling delete Message by id: " + messageId);
        messagesService.deleteMessage(messageId);
    }

}