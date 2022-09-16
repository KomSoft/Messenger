package com.itea.messenger.controller;

import com.itea.messenger.dto.*;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.service.DefaultChatUsersLinksService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/chatuserslinks")
@NoArgsConstructor
public class ChatsUsersLinksController {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    DefaultChatUsersLinksService chatUsersLinksService;

    @GetMapping
    public ResponseEntity<List<ChatUsersLinksDto>> findAll() {
        log.info("Handling find all Chat-users links");
        return ResponseEntity.ok().body(chatUsersLinksService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ChatUsersLinksDto> findById(@PathVariable("id") Long id) {
        log.info("Handling find Chat-users link with id:{}", id);
        try {
            return ResponseEntity.ok().body(chatUsersLinksService.findById(id));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ChatsShortDto>> getChatsByUserId(@PathVariable("id") Long id) {
        log.info("Handling find Chat-users link with userId:{}", id);
        try {
            return ResponseEntity.ok().body(chatUsersLinksService.getChatsByUserId(id));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<List<UsersShortDto>> getUsersByChatId(@PathVariable("id") Long id) {
        log.info("Handling find Chat-users link with chatId:{}", id);
        try {
            return ResponseEntity.ok().body(chatUsersLinksService.getUsersByChatId(id));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}