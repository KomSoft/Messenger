package com.itea.messenger.repository;

import com.itea.messenger.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatsRepository extends JpaRepository<Chats, Long> {

    Chats getChatById(Long id);

}
