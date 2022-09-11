package com.itea.messenger.controller;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.FilesService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
@NoArgsConstructor
@Log
public class FilesController {

    @Autowired
    FilesService filesService;

    @PostMapping
    public FilesDto saveFile(@RequestBody FilesDto fileDto) {
        log.info("Handling save File: " + fileDto);
        return filesService.saveFile(fileDto);
    }

    @GetMapping
    public List<FilesDto> findAll(){
        log.info("Handling find all files");
        return filesService.findAll();
    }

    @GetMapping("{id}")
    public FilesDto findById(@PathVariable("id") Long id) throws ValidationException {
        log.info("Handling find File by id:" + id);
        return filesService.findById(id);
    }
}



