package com.itea.messenger.repository;

import com.itea.messenger.entity.ChatMember;
import com.itea.messenger.entity.ChatMembersLinks;
import com.itea.messenger.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMembersLinksRepository extends JpaRepository<ChatMembersLinks, Long> {

    List<Users> findAllUsersByChatId(Long chatId);

//    added by KomSoft
    ChatMembersLinks findByChatIdAndUserId(Long chatId, Long userId);

}
