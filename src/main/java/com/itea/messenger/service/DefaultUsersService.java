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

    @Override
    public UsersDto saveUser(UsersDto usersDto) throws ValidationException {
        Users user = usersRepository.save(usersConverter.userFromDto(usersDto));
        return usersConverter.userToDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDto findById(Long id) throws ValidationException {
        Users user = usersRepository.findById(id).orElseThrow(() -> new ValidationException("No users with id:" + id));
        return usersConverter.userToDto(user);
    }

    @Override
    public UsersDto findByLogin(String login) {
        Users user = usersRepository.findByLogin(login);
        return user == null ? null : usersConverter.userToDto(user);
    }

    @Override
    public UsersDto findByName(String name) {
        Users user = usersRepository.findByName(name);
        return user == null ? null : usersConverter.userToDto(user);
    }

    @Override
    public List<UsersDto> findAll() {
        List<UsersDto> usersDto = new ArrayList<>();
        List<Users> users = usersRepository.findAll();
        for (Users user: users) {
            usersDto.add(usersConverter.userToDto(user));
        }
        return usersDto;
    }

}
