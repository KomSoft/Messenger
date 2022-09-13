package com.itea.messenger.controller;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.ChatService;
import com.itea.messenger.type.ChatTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@NoArgsConstructor
@Log
public class ChatsController {

    @Autowired
    ChatService chatService;

    @GetMapping("{id}")
    public ChatsDto getChatById(@PathVariable("id") Long chatId) {
        log.info("Handling get Chat by id: " + chatId);
        return chatService.getChatById(chatId);
    }

    @GetMapping
    public List<ChatsDto> getAll() {
        log.info("Handling get all Chats");
        return chatService.getAllChats();
    }

    @GetMapping("/types")
    public List<ChatTypeEnum> getChatTypes() {
        log.info("Handling get Chat types");
        return chatService.getChatTypes();
    }

    @PostMapping
    public ChatsDto createChat(@RequestBody ChatsDto chatsDto) throws ValidationException {
        log.info("Handling creating Chat");
        return chatService.createChat(chatsDto);
    }

    @PostMapping("{id}/adduser/{userId}")
    public void addUserToChat(@PathVariable("id") Long chatId, @PathVariable("userId") Long userId) {
        log.info("Handling add User id:" + userId + " to Chat id:" + chatId);
        chatService.addUserToChat(chatId, userId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteChat(@PathVariable("id") Long chatId) {
        log.info("Handling delete Chat by id: " + chatId);
        try {
            chatService.deleteChat(chatId);
            return ResponseEntity.ok().build();
        }
        catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chat with id:" + chatId + " doesn't exists");
        }
    }

//    TODO - public ? addChatUser(userId)
    /*
        @PostMapping("{userId}")
        public ResponseEntity<Void> createChat(@RequestBody ChatsDto chatsDto, @RequestBody Long userId) throws ValidationException {
            log.info("Handling creating chat. ");
            try {
                chatService.createChat(chatsDto, userId);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok().build();
        }
     }
   */

}