package com.itea.messenger.repository;

import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMembersLinksRepository extends JpaRepository<ChatUsersLinks, Long> {

    List<Users> findAllUsersByChatId(Long chatId);

//    added by KomSoft
    ChatUsersLinks findByChatIdAndUserId(Long chatId, Long userId);

}
