package com.itea.messenger.controller;

import com.itea.messenger.dto.ChatsDto;
import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.ChatUsersLinksService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatuserslinks")
@AllArgsConstructor
@Log
public class ChatUsersLinksController {
    private final ChatUsersLinksService chatUsersLinksService;

    @PostMapping
    public ChatUsersLinksDto saveChatMembersLink(@RequestBody ChatUsersLinksDto chatUsersLinksDto) throws ValidationException {
        log.info("Handling save chat users links: " + chatUsersLinksDto);
        return chatUsersLinksService.saveChatUsersLink(chatUsersLinksDto);
    }

    @GetMapping
    public List<ChatUsersLinksDto> findAll() {
        log.info("Handling find all chat users links");
        return chatUsersLinksService.findAll();
    }

    @GetMapping("/{id}")
    public ChatUsersLinksDto findById(@PathVariable("id") Long id) throws ValidationException {
        log.info("Handling find by id chat users link with id:" + id);
        return chatUsersLinksService.findById(id);
    }

    @GetMapping("/{id}/user")
    public List<ChatsDto> getChatsByUserId(@PathVariable("id") Long id) {
        log.info("Handling find by id chat users link with id:" + id);
        return chatUsersLinksService.getChatsByUserId(id);
    }

    @GetMapping("/{id}/chat")
    public List<UsersDto> getUsersByChatId(@PathVariable("id") Long id) {
        log.info("Handling find by id chat users link with id:" + id);
        return chatUsersLinksService.getUsersByChatId(id);
    }

}