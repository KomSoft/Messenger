package com.itea.messenger.controller;

import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.service.StatusLinksService;
import com.itea.messenger.service.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statusLinks")
@AllArgsConstructor
@Log
public class StatusLinksController {
    private final StatusLinksService statusLinksService;

    @PostMapping
    public StatusLinksDto saveStatusLinks(@RequestBody StatusLinksDto statusLinksDto) throws ValidationException {
        log.info("Handling save status links request: " + statusLinksDto.toString());
        return statusLinksService.saveStatusLink(statusLinksDto);
    }

    @GetMapping("/{id}")
    public StatusLinksDto findById(@PathVariable Long id) throws ValidationException {
        log.info("Handling find by id: " + id);
        return statusLinksService.findById(id);
    }

}
