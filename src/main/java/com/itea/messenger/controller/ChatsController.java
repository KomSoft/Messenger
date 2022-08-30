package com.itea.messenger.controller;

import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.Chat;
import com.itea.messenger.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

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

}