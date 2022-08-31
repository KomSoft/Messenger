package com.itea.messenger.controller;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log
public class UsersController {
    private final UsersService usersService;

    @PostMapping
    public UsersDto saveUsers(@RequestBody UsersDto usersDto) throws ValidationException {
        log.info("Handling save users: " + usersDto);
        return usersService.saveUser(usersDto);
    }

    @GetMapping("/{id}")
    public UsersDto findById(@PathVariable("id") Long id) throws ValidationException {
        log.info("Handling find user by id: " + id);
        return usersService.findById(id);
    }

    @GetMapping("/{login}")
    public UsersDto getByLogin(@PathVariable("login") String login) throws ValidationException {
        log.info("Handling find user by login: " + login);
        return usersService.findByLogin(login);
    }

    @GetMapping("/{name}")
    public UsersDto getByName(@PathVariable("name") String name) throws ValidationException {
        log.info("Handling find user by login: " + name);
        return usersService.findByName(name);
    }

    @GetMapping
    public List<UsersDto> findAll() {log.info("Handling find all service");
        return usersService.findAll();
    }

}