package com.itea.messenger.repository;

import com.itea.messenger.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByLogin(String login);
    Users findByName(String name);
}