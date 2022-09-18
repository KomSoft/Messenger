package com.itea.messenger.service;

import com.itea.messenger.converter.UsersConverter;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultUsersService implements UsersService{
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersConverter usersConverter;

    @Override
    public UsersDto saveUser(UsersDto usersDto) throws ValidationException {
        Users user = usersConverter.userFromDto(usersDto);
        Users user1 = usersRepository.findByLogin(user.getLogin());
        if (user1 != null) {
            throw new ValidationException("User with login '" + user.getLogin() + "' already exists");
        }
        return usersConverter.userToDto(usersRepository.save(user));
//        TODO !!!! Save user with the same avatar removes both user
    }

    @Override
    public void deleteUser(Long id) throws EmptyResultDataAccessException {
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDto findById(Long id) throws NotFoundException {
        Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("No users with id:" + id));
        return usersConverter.userToDto(user);
    }

    @Override
    public UsersDto findByLogin(String login) throws NotFoundException {
        Users user = usersRepository.findByLogin(login);
        if (user == null) {
            throw new NotFoundException("User with login '" + login + "' not found");
        }
        return usersConverter.userToDto(user);
    }

    @Override
    public UsersDto findByName(String name) throws NotFoundException {
        Users user = usersRepository.findByName(name);
        if (user == null) {
            throw new NotFoundException("User with name '" + name + "' not found");
        }
        return usersConverter.userToDto(user);
    }

    @Override
    public List<UsersDto> findAll() throws NotFoundException {
        List<Users> users = usersRepository.findAll();
        if (users.size() == 0) {
            throw new NotFoundException("No Users records in repository");
        }
        return users.stream().map(usersConverter::userToDto).collect(Collectors.toList());
    }

}
