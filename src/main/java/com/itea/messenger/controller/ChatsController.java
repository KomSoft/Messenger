package com.itea.messenger.controller;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chats")
@AllArgsConstructor
@Log
public class ChatsController {
    private final ChatService chatService;

    @GetMapping("{Id}")
    public ChatsDto getChatById(@PathVariable("Id") Long chatId) {
        log.info("Handling get chat by ID: " + chatId);
        return chatService.getChatById(chatId);
    }

    @GetMapping
    public List<ChatsDto> getAll() {
        log.info("Handling get all chats");
        return chatService.getAllChats();
    }

//    TODO - public ChatsDto createChat(name, description)
    @PostMapping
    public ResponseEntity<Void> createChat(@RequestBody ChatsDto chatsDto ) throws ValidationException {
        log.info("Handling creating chat");
        chatService.createChat(chatsDto);
        return ResponseEntity.ok().build();
    }

//    TODO - public void deleteChat(id)
    @DeleteMapping("{id}")
    public ResponseEntity deleteChat(@PathVariable("id") Long chatId) {
        log.info("Handling delete chat by id: " + chatId);
        try {
            chatService.deleteChat(chatId);
            return ResponseEntity
                    .ok().build();
        }
        catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chat with id " + chatId + " doesn't exists");
        }
    }

//    TODO - public ? addChatUser(userId)

}