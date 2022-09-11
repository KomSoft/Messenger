package com.itea.messenger.controller;

import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.StatusLinksService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statusLinks")
@NoArgsConstructor
@Log
public class StatusLinksController {

    @Autowired
    StatusLinksService statusLinksService;

    @GetMapping("{id}")
    public StatusLinksDto findById(@PathVariable("id") Long id) throws ValidationException {
        log.info("Handling find status link by id:" + id);
        return statusLinksService.findById(id);
    }

    @GetMapping("{id}/message")
    public List<StatusLinksDto> findByMessageId(@PathVariable("id") Long messageId) throws ValidationException {
        log.info("Handling find status link by messageId:" + messageId);
        return statusLinksService.findByMessageId(messageId);
    }

}
