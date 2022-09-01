package com.itea.messenger.service;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.exception.ValidationException;
import java.util.List;

public interface UsersService {
    UsersDto saveUser(UsersDto usersDto) throws ValidationException;
    void deleteUser(Long id);
    UsersDto findById(Long id) throws ValidationException;
    UsersDto findByLogin(String login);
    UsersDto findByName(String name);
    List<UsersDto> findAll();
}
