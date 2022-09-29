package com.itea.messenger.service;

import com.itea.messenger.entity.Users;
import com.itea.messenger.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        Users myUser = usersRepository.findByLogin(userLogin);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown myUser: " + userLogin);
        }
        UserDetails user = User.builder().username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }
}
