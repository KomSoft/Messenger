package com.itea.messenger.repository;

import com.itea.messenger.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {

    List<Messages> findAllMessagesByChatId(Long chatId);
    List<Messages> findMessagesByChatIdAndDateTimeAfter(Long chatId, LocalDateTime dateTimeFrom);
    List<Messages> findMessagesByChatIdAndDateTimeAfterOrderByDateTime(Long chatId, LocalDateTime dateTimeFrom);
    void deleteAllByChatId(Long chatId);
    void deleteAllByUserId(Long userId);
    void deleteAllByChatIdAndUserId(Long chatId, Long userId);

}
