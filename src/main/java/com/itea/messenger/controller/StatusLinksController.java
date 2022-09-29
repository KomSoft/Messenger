package com.itea.messenger.controller;

import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.service.StatusLinksService;
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
@RequestMapping("/statusLinks")
@NoArgsConstructor
public class StatusLinksController {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private StatusLinksService statusLinksService;

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
//    public ResponseEntity<StatusLinksDto> findById(@PathVariable("id") Long id) {
        log.info("Handling find Status Link by id:{}", id);
        try {
            return ResponseEntity.ok(statusLinksService.findById(id));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/message/{id}")
    public ResponseEntity findByMessageId(@PathVariable("id") Long messageId) {
//    public ResponseEntity<List<StatusLinksDto>> findByMessageId(@PathVariable("id") Long messageId) {
        log.info("Handling find Status Links by messageId:{}", messageId);
        try {
            return ResponseEntity.ok(statusLinksService.findByMessageId(messageId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
