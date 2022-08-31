package com.itea.messenger.controller;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@AllArgsConstructor
@Log
public class ChatsController {
    private final ChatService chatService;

    @GetMapping("{Id}")
    public Chats getChatById(@PathVariable("Id") Long chatId) {
        log.info("Handling get chat by ID: " + chatId);
        return chatService.getChatById(chatId);
    }

    @GetMapping
    public List<ChatsDto> getAll() {
        log.info("Handling get all chats");
        return chatService.getAllChats();
    }

}