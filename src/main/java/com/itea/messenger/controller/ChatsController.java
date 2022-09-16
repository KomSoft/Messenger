package com.itea.messenger.controller;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.DefaultChatService;
import com.itea.messenger.type.ChatTypeEnum;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/chats")
@NoArgsConstructor
public class ChatsController {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    DefaultChatService chatService;

    @GetMapping("{id}")
    public ResponseEntity<ChatsDto> getChatById(@PathVariable("id") Long chatId) {
        log.info("Handling get Chat by id:{}", chatId);
        try {
            ChatsDto chatsDto = chatService.getChatById(chatId);
            return ResponseEntity.ok(chatsDto);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ChatsDto>> getAll() {
        log.info("Handling get All Chats");
        try {
            return ResponseEntity.ok(chatService.getAllChats());
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/types")
    public List<ChatTypeEnum> getChatTypes() {
        log.info("Handling get Chat types");
        return chatService.getChatTypes();
    }

    @PostMapping
    public ResponseEntity<ChatsDto> createChat(@RequestBody ChatsDto chatsDto) {
        log.info("Handling creating Chat with DTO:{}", chatsDto);
        try {
            return ResponseEntity.ok(chatService.createChat(chatsDto));
        } catch (ValidationException e) {
            log.error("{} (with {})", e.getMessage(), chatsDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(chatsDto);
        }
    }

    @Transactional
    @PostMapping("/member")
    public ResponseEntity addUserToChat(@RequestParam Long chatId, @RequestParam Long userId) {
        log.info("Handling add User id:{} to Chat id:{}", userId, chatId);
        try {
            chatService.addUserToChat(chatId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("User id:" + userId + " has added to Chat id:" + chatId);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't add User id:" + userId + " to Chat id:" + chatId);
        }

    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity deleteChat(@PathVariable("id") Long chatId) {
        log.info("Handling delete Chat by id:{}", chatId);
        try {
            chatService.deleteChat(chatId);
            return ResponseEntity.ok().build();
        } catch(EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat with id:" + chatId + " doesn't exists");
        }
    }

//    TODO -
//    public ResponseEntity removeUserFromChat(@RequestParam Long chatId, @RequestParam Long userId) {

}