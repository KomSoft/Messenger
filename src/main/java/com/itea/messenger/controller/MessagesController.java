package com.itea.messenger.controller;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.service.MessagesService;
import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessagesController {
    private static final Logger log = LogManager.getLogger(MessagesController.class);
    private final MessagesService messagesService;

//    @SneakyThrows

    @PostMapping(value = "/save")
    public MessagesDto saveMessages(@RequestBody MessagesDto messageDto) {
        log.info("Handling save message: " + messageDto);
        return messagesService.saveMessage(messageDto);
    }


    @GetMapping("/getById")
    public MessagesDto getMessageById(@RequestParam Long messageId) {
        log.info("Handling get message by ID: " + messageId);
        return messagesService.getMessageById(messageId);
    }


    @GetMapping("/getAllByChatId")
    public List<MessagesDto> getAllMessagesByChatId(@RequestParam Long chatId) {
        log.info("Handling get all messages by chat ID: " + chatId);
        return messagesService.getAllMessagesByChatId(chatId);
    }


    @DeleteMapping("/delete")
    public void deleteMessage(@RequestParam Long messageId) {
        log.info("Handling delete message by ID: " + messageId);
        messagesService.deleteMessage(messageId);
    }

}