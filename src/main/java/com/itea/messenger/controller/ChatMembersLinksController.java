package com.itea.messenger.controller;

import com.itea.messenger.dto.ChatMembersLinksDto;
import com.itea.messenger.service.ChatMembersLinksService;
import com.itea.messenger.service.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memberslink")
@AllArgsConstructor
@Log
public class ChatMembersLinksController {
    private final ChatMembersLinksService chatMembersLinksService;

    @PostMapping
    public ChatMembersLinksDto saveChatMembersLink(@RequestBody ChatMembersLinksDto chatMembersLinksDto) throws ValidationException {
        log.info("Handling save chat members link: " + chatMembersLinksDto);
        return chatMembersLinksService.saveChatMembersLink(chatMembersLinksDto);
    }

    @GetMapping
    public List<ChatMembersLinksDto> findAll(){
        log.info("Handling find all chat members links");
        return chatMembersLinksService.findAll();
    }

    @GetMapping("/{id}")
    public ChatMembersLinksDto findById(@PathVariable Long id)throws ValidationException {
        log.info("Handling find by id chat members link");
        return chatMembersLinksService.findById(id);
    }
}