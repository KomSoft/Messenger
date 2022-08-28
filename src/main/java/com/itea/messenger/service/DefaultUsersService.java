package com.itea.messenger.service;

import com.itea.messenger.converter.UsersConverter;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.Users;
import com.itea.messenger.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultUsersService implements UsersService{
    private final UsersRepository usersRepository;
    private final UsersConverter usersConverter;

    private void validateUserDto(UsersDto usersDto) throws ValidationException {
        if (isNull(usersDto)){throw new ValidationException("Object is null");
        }
        if (isNull(usersDto.getLogin()) || usersDto.getLogin().isEmpty()){
            throw new ValidationException("Login is empty");
        }
        if (isNull(usersDto.getName()) || usersDto.getName().isEmpty()) {
            throw new ValidationException("Name is empty");
        }
    }

    @Override
    public UsersDto saveUser(UsersDto usersDto) throws ValidationException {
        validateUserDto(usersDto);
        Users users = usersRepository.save(usersConverter.usersEntityFromDto(usersDto));
        System.out.println(usersDto);
        System.out.println(users);
        return usersConverter.dtoFromUsersEntity(users);
    }

    @Override
    public UsersDto findById(Long id) throws ValidationException {
        Users users = usersRepository.findById(id).orElseThrow(( ) -> new ValidationException("No users with this id"));
        return usersConverter.dtoFromUsersEntity(users);
    }

    @Override
    public List<UsersDto> findAll() {
        List<UsersDto> dtoList = new ArrayList<>();
        List<Users> list = usersRepository.findAll();
        for (Users users: list
        ) {
            dtoList.add(usersConverter.dtoFromUsersEntity(users));
        }
        return dtoList;
    }
}
