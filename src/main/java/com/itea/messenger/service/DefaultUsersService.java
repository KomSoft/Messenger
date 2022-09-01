package com.itea.messenger.service;

import com.itea.messenger.converter.UsersConverter;
import com.itea.messenger.dto.UsersDto;
import com.itea.messenger.entity.Users;
import com.itea.messenger.exception.ValidationException;
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
        Users user = usersRepository.save(usersConverter.usersEntityFromDto(usersDto));
        System.out.println(usersDto);
        System.out.println(user);
        return usersConverter.dtoFromUsersEntity(user);
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDto findById(Long id) throws ValidationException {
        Users user = usersRepository.findById(id).orElseThrow(() -> new ValidationException("No users with this id"));
        return usersConverter.dtoFromUsersEntity(user);
    }

    @Override
    public UsersDto findByLogin(String login) {
        Users user = usersRepository.findByLogin(login);
        return user == null ? null : usersConverter.dtoFromUsersEntity(user);
    }

    @Override
    public UsersDto findByName(String name) {
        Users user = usersRepository.findByName(name);
        return user == null ? null : usersConverter.dtoFromUsersEntity(user);
    }

    @Override
    public List<UsersDto> findAll() {
        List<UsersDto> dtoList = new ArrayList<>();
        List<Users> list = usersRepository.findAll();
        for (Users user: list) {
            dtoList.add(usersConverter.dtoFromUsersEntity(user));
        }
        return dtoList;
    }

}
