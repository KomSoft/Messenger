package com.itea.messenger.repository;

import com.itea.messenger.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatsRepository extends JpaRepository<Chats, Long> {
    Optional<Chats> findByName(String name);

}
