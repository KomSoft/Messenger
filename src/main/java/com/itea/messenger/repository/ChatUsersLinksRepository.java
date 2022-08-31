package com.itea.messenger.repository;

import com.itea.messenger.entity.Chats;
import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUsersLinksRepository extends JpaRepository<ChatUsersLinks, Long> {

    List<Users> getUsersByChatId(Long chatId);
//    List<Chat> getUsersByChatId(Long userId);
    List<Chats> getChatsByUserId(Long userId);
    ChatUsersLinks findByChatIdAndUserId(Long chatId, Long userId);
}
