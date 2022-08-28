package com.itea.messenger.controller;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.service.FilesService;
import com.itea.messenger.service.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
@Log
public class FilesController {
    private final FilesService filesService;

    @PostMapping
    public FilesDto saveFile(@RequestBody FilesDto filesDto)throws ValidationException {
        log.info("Handling save file: " + filesDto);
        return filesService.saveFile(filesDto);

    }
    @GetMapping
    public List<FilesDto> findAll(){
        log.info("Handling find all files");
        return filesService.findAll();
    }

    @GetMapping("/{id}")
    public FilesDto findById(@PathVariable Long id)throws ValidationException {
        log.info("Handling find by id");
        return filesService.findById(id);
    }
}



