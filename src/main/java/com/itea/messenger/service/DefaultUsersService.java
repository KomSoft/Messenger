package com.itea.messenger.service;

import com.itea.messenger.converter.FilesConverter;
import com.itea.messenger.converter.UsersConverter;
import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.Chats;
import com.itea.messenger.entity.Files;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.interfaces.ChatsInfo;
import com.itea.messenger.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultUsersService implements UsersService{
    @Autowired
    private UsersConverter usersConverter;
    @Autowired
    private FilesConverter filesConverter;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private ChatsUsersLinksRepository chatsUsersLinksRepository;
    @Autowired
    private MessagesRepository messagesRepository;

    @Transactional
    @Override
    public UsersDto saveUser(UsersDto usersDto) throws ValidationException {
        Users user = usersConverter.userFromDto(usersDto);
        Users user1 = usersRepository.findByLogin(user.getLogin());
        if (user1 != null) {
            throw new ValidationException(MessageFormat.format("User with login \"{0}\" already exists", user.getLogin()));
        }
        if (usersDto.getAvatarName() != null && !usersDto.getAvatarName().isEmpty()) {
            Files avatar = filesConverter.fileEntityFromDto(new FilesDto(usersDto.getAvatarId(), usersDto.getAvatarName()));
            filesRepository.save(avatar);
            user.setAvatar(avatar);
        }
        return usersConverter.userToDto(usersRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteUser(Long id) throws EmptyResultDataAccessException {
        messagesRepository.deleteAllByUserId(id);
//        delete cascade automatic
//        statusLinksRepository.deleteAllByUserId(id);
//        chatsUsersLinksRepository.deleteAllByUserId(id);
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDto findById(Long id) throws NotFoundException {
        Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User id:" + id + " not found!"));
        return usersConverter.userToDto(user);
    }

    @Override
    public UsersDto findByLogin(String login) throws NotFoundException {
        Users user = usersRepository.findByLogin(login);
        if (user == null) {
            throw new NotFoundException(MessageFormat.format("User with login \"{0}\" not found", login));
        }
        return usersConverter.userToDto(user);
    }

    @Override
    public UsersDto findByName(String name) throws NotFoundException {
        Users user = usersRepository.findByName(name);
        if (user == null) {
            throw new NotFoundException(MessageFormat.format("User with name \"{0}\" not found", name));
        }
        return usersConverter.userToDto(user);
    }

    @Override
    public List<UsersDto> findAll() throws NotFoundException {
        List<Users> users = usersRepository.findAll();
        if (users.size() == 0) {
            throw new NotFoundException("No Users records found");
        }
        return users.stream().map(usersConverter::userToDto).collect(Collectors.toList());
    }

}
