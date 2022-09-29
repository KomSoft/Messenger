package com.itea.messenger.converter;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.dto.UsersShortDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.interfaces.UsersInfo;
import com.itea.messenger.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersConverter {

    @Autowired
    private FilesRepository filesRepository;

    private void validateUserDto(UsersDto usersDto) throws ValidationException {
        if (usersDto == null) {
            throw new ValidationException("[UserDto] Object is null");
        }
        if (usersDto.getLogin() == null || usersDto.getLogin().isEmpty()) {
            throw new ValidationException("[UserDto] Login is empty");
        }
        if (usersDto.getName() == null || usersDto.getName().isEmpty()) {
            throw new ValidationException("[UserDto] Name is empty");
        }
        if (usersDto.getRole() == null || usersDto.getRole().isEmpty()) {
            throw new ValidationException("[UserDto] Role is empty");
        }
        if (usersDto.getAge() < Users.MIN_AGE) {
            throw new ValidationException("[UserDto] Age is empty or less than " + Users.MIN_AGE);
        }
    }

    public Users userFromDto(UsersDto userDto) throws ValidationException {
        validateUserDto(userDto);
        Users user = new Users();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());
        user.setLogin(userDto.getLogin());
        user.setRole(userDto.getRole());
//        check correct file_id for existing users
//        for new user we'll check and save file_name in saveUser method before save User
        if (userDto.getAvatarId() != null && userDto.getAvatarId() > 0) {
            Files file = filesRepository.findById(userDto.getAvatarId()).orElseThrow(() -> new ValidationException("[UserDto] File id:" + userDto.getAvatarId() + " is incorrect"));
            user.setAvatar(file);
        }
        return user;
    }

    public UsersDto userToDto(Users user) {
        UsersDto userDto = new UsersDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassword("********");
        userDto.setAge(user.getAge());
        userDto.setLogin(user.getLogin());
        userDto.setRole(user.getRole());
        if (user.getAvatar() != null) {
            userDto.setAvatarId(user.getAvatar().getId());
            userDto.setAvatarName(user.getAvatar().getFileName());
        }
        return userDto;
    }

    public UsersShortDto userToShortDto(Users user) {
        UsersShortDto userDto = new UsersShortDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        if (user.getAvatar() != null) {
            userDto.setAvatarId(user.getAvatar().getId());
            userDto.setAvatarName(user.getAvatar().getFileName());
        }
        return userDto;
    }

    public UsersShortDto userToShortDto(UsersInfo user) {
        UsersShortDto userDto = new UsersShortDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        if (user.getAvatar() != null) {
            userDto.setAvatarId(user.getAvatar().getId());
            userDto.setAvatarName(user.getAvatar().getFileName());
        }
        return userDto;
    }

}
