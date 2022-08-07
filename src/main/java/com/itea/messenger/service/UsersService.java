package com.itea.messenger.service;

import com.itea.messenger.dto.UsersDto;

import java.util.List;

public interface UsersService {
    UsersDto saveUser(UsersDto usersDto)throws ValidationException;
    UsersDto findById(Long id)throws ValidationException;
    List<UsersDto> findAll();
}
