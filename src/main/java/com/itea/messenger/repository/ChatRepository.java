package com.itea.messenger.repository;

import com.itea.messenger.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat getChatById(Long id);

//    Chat findByName(String name);
}
