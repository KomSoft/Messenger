package com.itea.messenger.controller;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@NoArgsConstructor
@Log
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping
    public UsersDto saveUsers(@RequestBody UsersDto userDto) throws ValidationException {
        log.info("Handling save User: " + userDto);
        return usersService.saveUser(userDto);
    }

    @GetMapping("{id}")
    public UsersDto findById(@PathVariable("id") Long id) throws ValidationException {
        log.info("Handling find User by id:" + id);
        return usersService.findById(id);
    }

    @GetMapping("{login}/login")
    public UsersDto getByLogin(@PathVariable("login") String login) {
        log.info("Handling find User by login:" + login);
        return usersService.findByLogin(login);
    }

    @GetMapping("{name}/name")
    public UsersDto getByName(@PathVariable("name") String name) {
        log.info("Handling find User by name:" + name);
        return usersService.findByName(name);
    }

    @GetMapping
    public List<UsersDto> findAll() {
        log.info("Handling find all Users");
        return usersService.findAll();
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Long userId) {
        log.info("Handling delete User by id:" + userId);
        usersService.deleteUser(userId);
    }

}