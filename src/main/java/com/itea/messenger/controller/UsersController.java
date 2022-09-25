package com.itea.messenger.controller;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.UsersService;
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
@RequestMapping("/users")
@NoArgsConstructor
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private UsersService usersService;

    @PostMapping
//    public ResponseEntity<UsersDto> saveUsers(@RequestBody UsersDto userDto) {
    public ResponseEntity saveUsers(@RequestBody UsersDto userDto) {
        log.info("Handling save User: {}", userDto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usersService.saveUser(userDto));
        } catch (ValidationException e) {
            log.error("{} (with {})", e.getMessage(),userDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
//    public ResponseEntity<UsersDto> findById(@PathVariable("id") Long id) {
        log.info("Handling find User by id:{}", id);
        try {
            return ResponseEntity.ok(usersService.findById(id));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/login/{login}")
//    public ResponseEntity<UsersDto> getByLogin(@PathVariable("login") String login) {
    public ResponseEntity getByLogin(@PathVariable("login") String login) {
        log.info("Handling find User by login '{}'", login);
        try {
            return ResponseEntity.ok(usersService.findByLogin(login));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getByName(@PathVariable("name") String name) {
//    public ResponseEntity<UsersDto> getByName(@PathVariable("name") String name) {
        log.info("Handling find User by name '{}'", name);
        try {
            return ResponseEntity.ok(usersService.findByName(name));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity findAll() {
//    public ResponseEntity<List<UsersDto>> findAll() {
        log.info("Handling find all Users");
        try {
            return ResponseEntity.ok(usersService.findAll());
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long userId) {
        log.info("Handling delete User by id:{}", userId);
        try {
            usersService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch(EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id:" + userId + " doesn't exists");
        }
    }

}