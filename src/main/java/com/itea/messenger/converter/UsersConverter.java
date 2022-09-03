package com.itea.messenger.converter;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.Users;
import com.itea.messenger.interfaces.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UsersConverter {
    public Users usersEntityFromDto(UsersDto usersDto) {
        Users user = new Users();
        user.setId(usersDto.getId());
        user.setName(usersDto.getName());
        user.setAge(usersDto.getAge());
        user.setLogin(usersDto.getLogin());
        user.setPassword(usersDto.getPassword());
        user.setPhotoId(usersDto.getPhotoId());
        return user;
    }

    public UsersDto dtoFromUsersEntity(Users user) {
        UsersDto usersDto = new UsersDto();
        usersDto.setId(user.getId());
        usersDto.setAge(user.getAge());
        usersDto.setName(user.getName());
        usersDto.setLogin(user.getLogin());
        usersDto.setPassword(user.getPassword());
        usersDto.setPhotoId(user.getPhotoId());
        return usersDto;
    }

    public UsersDto dtoFromUserInfo(UserInfo user) {
        UsersDto usersDto = new UsersDto();
        usersDto.setId(user.getId());
        usersDto.setAge(user.getAge());
        usersDto.setName(user.getName());
        usersDto.setLogin(user.getLogin());
        usersDto.setPassword(user.getPassword());
        usersDto.setPhotoId(user.getPhotoId());
        return usersDto;
    }
}
