package com.itea.messenger.converter;

import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersConverter {
    public Users usersEntityFromDto(UsersDto usersDto) {
        Users users = new Users();
        users.setId(usersDto.getId());
        users.setName(usersDto.getName());
        users.setAge(usersDto.getAge());
        users.setLogin(usersDto.getLogin());
        users.setPassword(usersDto.getPassword());
        users.setPhotoId(usersDto.getPhotoId());
        return users;
    }
    public UsersDto dtoFromUsersEntity(Users users){
        UsersDto usersDto = new UsersDto();
        usersDto.setId(users.getId());
        usersDto.setAge(users.getAge());
        usersDto.setName(users.getName());
        usersDto.setLogin(users.getLogin());
        usersDto.setPassword(users.getPassword());
        usersDto.setPhotoId(users.getPhotoId());
        return usersDto;
    }
}
