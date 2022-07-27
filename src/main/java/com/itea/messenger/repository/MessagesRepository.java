package com.itea.messenger.repository;

import com.itea.messenger.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    public List<Messages> findAllMessagesByChatId(Long chatId);
//    no additional functions now
}
