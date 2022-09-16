package com.itea.messenger.repository;

import com.itea.messenger.entity.ChatsUsersLinks;
import com.itea.messenger.interfaces.ChatsInfo;
import com.itea.messenger.interfaces.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ChatsUsersLinksRepository extends JpaRepository<ChatsUsersLinks, Long> {

//    TODO - why query doesn't work when users.avatar_id == null ?
//    @Query("select u.id as id, u.name as name, u.avatar as avatar " +
    @Query("select u.id as id, u.name as name " +
            "from Users u, ChatUsersLinks link where link.chatId=?1 and u.id=link.userId")
    List<UsersInfo> getUsersByChatId(Long chatId);

    @Query("select chat.id as id, chat.name as name, chat.description as description, chat.chatType as chatType " +
            "from Chats chat, ChatUsersLinks link where chat.id=link.chatId and link.userId=?1")
    List<ChatsInfo> getChatsByUserId(Long userId);

    ChatsUsersLinks findByChatIdAndUserId(Long chatId, Long userId);

    void deleteAllByChatId(Long chatId);
}
