package com.itea.messenger.repository;

import com.itea.messenger.entity.ChatUsersLinks;
import com.itea.messenger.interfaces.ChatsInfo;
import com.itea.messenger.interfaces.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ChatUsersLinksRepository extends JpaRepository<ChatUsersLinks, Long> {

    @Query("select user.id as id, user.name as name, user.password as password, user.login as login,  " +
            "user.avatar as avatar, user.age as age " +
            "from Users user, ChatUsersLinks link where user.id=link.userId and link.chatId=?1")
    List<UserInfo> getUsersByChatId(Long chatId);

    @Query("select chat.id as id, chat.name as name, chat.description as description, chat.chatType as chatType " +
            "from Chats chat, ChatUsersLinks link where chat.id=link.chatId and link.userId=?1")
    List<ChatsInfo> getChatsByUserId(Long userId);

    ChatUsersLinks findByChatIdAndUserId(Long chatId, Long userId);

    void deleteAllByChatId(Long chatId);
}
