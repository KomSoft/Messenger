package com.itea.messenger.service;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import java.util.List;

public interface UsersService {
    UsersDto saveUser(UsersDto usersDto) throws ValidationException;
    void deleteUser(Long id);
    UsersDto findById(Long id) throws NotFoundException;
    UsersDto findByLogin(String login) throws NotFoundException;
    UsersDto findByName(String name) throws NotFoundException;
    List<UsersDto> findAll() throws NotFoundException;
}
