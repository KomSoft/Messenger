package com.itea.messenger.controller;

import com.itea.messenger.dto.ChatDto;
import com.itea.messenger.entity.Chat;
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
    public Chat getChatById(@PathVariable("Id") Long chatId) {
        log.info("Handling get chat by ID: " + chatId);
        return chatService.getChatById(chatId);
    }

    @GetMapping
    public List<ChatDto> getAll() {
        log.info("Handling get all chats");
        return chatService.getAllChats();
    }

}