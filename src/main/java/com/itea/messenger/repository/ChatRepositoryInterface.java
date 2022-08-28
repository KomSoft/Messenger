package com.itea.messenger.repository;

import com.itea.messenger.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepositoryInterface extends JpaRepository<Chat, Long> {

    Chat findByChatId(Long id);

    Chat findByName(String name);
}
