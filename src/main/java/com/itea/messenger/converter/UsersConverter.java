package com.itea.messenger.converter;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.dto.UsersShortDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.interfaces.UsersInfo;
import com.itea.messenger.repository.FilesRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import static java.util.Objects.isNull;

@Component
public class UsersConverter {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private FilesRepository filesRepository;

    private void validateUserDto(UsersDto usersDto) throws ValidationException {

        if (isNull(usersDto)) {
            throw new ValidationException("[UserDto] Object is null");
        }
        if (isNull(usersDto.getLogin()) || usersDto.getLogin().isEmpty()) {
            throw new ValidationException("[UserDto] Login is empty");
        }
        if (isNull(usersDto.getName()) || usersDto.getName().isEmpty()) {
            throw new ValidationException("[UserDto] Name is empty");
        }
        if (isNull(usersDto.getAge()) || usersDto.getAge() < Users.MIN_AGE) {
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
//        TODO - extend logic when we send file body with FileDto object
//        now we should save file before convert User to get avatarId
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
