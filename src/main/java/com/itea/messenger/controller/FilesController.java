package com.itea.messenger.controller;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.FilesService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/files")
@NoArgsConstructor
public class FilesController {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private FilesService filesService;

    @PostMapping
    public ResponseEntity saveFile(@RequestBody FilesDto fileDto) {
//    public ResponseEntity<FilesDto> saveFile(@RequestBody FilesDto fileDto) {
        log.info("Handling save File: {}", fileDto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(filesService.saveFile(fileDto));
        } catch (ValidationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity findAll(){
//    public ResponseEntity<List<FilesDto>> findAll(){
        log.info("Handling find all files");
        try {
            return ResponseEntity.ok(filesService.findAll());
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) throws ValidationException {
//    public ResponseEntity<FilesDto> findById(@PathVariable("id") Long id) throws ValidationException {
        log.info("Handling find File by id:{}", id);
        try {
            return ResponseEntity.ok(filesService.findById(id));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        log.info("Handling delete File by id:{}", id);
        try {
            filesService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with id:" + id + " doesn't exists");
        }
    }

}



