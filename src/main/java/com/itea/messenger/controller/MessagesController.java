package com.itea.messenger.controller;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.service.MessagesService;
import lombok.AllArgsConstructor;
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

    @PostMapping
    public MessagesDto saveMessages(@RequestBody MessagesDto messageDto) {
        log.info("Handling save message: " + messageDto);
        return messagesService.saveMessage(messageDto);
    }

    @GetMapping("{Id}")
    public MessagesDto getMessageById(@PathVariable("Id") Long messageId) {
        log.info("Handling get message by ID: " + messageId);
        return messagesService.getMessageById(messageId);
    }

    @GetMapping("{Id}/chat")
    public List<MessagesDto> getAllMessagesByChatId(@PathVariable("Id") Long chatId) {
        log.info("Handling get all messages by chat ID: " + chatId);
        return messagesService.getAllMessagesByChatId(chatId);
    }

    @DeleteMapping("{Id}")
    public void deleteMessage(@PathVariable("Id") Long messageId) {
        log.info("Handling delete message by ID: " + messageId);
        messagesService.deleteMessage(messageId);
    }

}