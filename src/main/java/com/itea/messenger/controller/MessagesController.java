package com.itea.messenger.controller;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.MessagesService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/messages")
@NoArgsConstructor
public class MessagesController {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private MessagesService messagesService;

    @PostMapping
    public ResponseEntity<MessagesDto> saveMessage(@RequestBody MessagesDto messageDto) {
        log.info("Handling save Message: {}", messageDto);
        try {
            return ResponseEntity.ok(messagesService.saveMessage(messageDto));
        } catch (ValidationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<MessagesDto> getById(@PathVariable("id") Long messageId) {
        log.info("Handling get Message by id:{}", messageId);
        try {
            return ResponseEntity.ok(messagesService.getById(messageId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessagesDto>> getAllMessagesByChatId(@PathVariable("chatId") Long chatId) {
        log.info("Handling get All Messages by chatId:{}", chatId);
        try {
            return ResponseEntity.ok(messagesService.getAllMessagesByChatId(chatId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/chat/{chatId}/user/{userId}")
    public ResponseEntity<List<MessagesDto>> getMessagesForUserByChatId(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) {
        log.info("Handling get messages for User id:{} by chatId:{}", userId, chatId);
        try {
            return ResponseEntity.ok(messagesService.getMessagesForUserByChatId(chatId, userId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteMessage(@PathVariable("id") Long messageId) {
        log.info("Handling delete Message by id:{}", messageId);
        try {
            messagesService.deleteMessage(messageId);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException | ValidationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message with id:" + messageId + " doesn't exists");
        }
    }

}