package com.itea.messenger.repository;

import com.itea.messenger.entity.ChatsUsersLinks;
import com.itea.messenger.interfaces.ChatsInfo;
import com.itea.messenger.interfaces.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ChatsUsersLinksRepository extends JpaRepository<ChatsUsersLinks, Long> {

    @Query("SELECT u.id AS id, u.name AS name, f.id AS avatarId, f.fileName AS avatarName " +
            "FROM ChatsUsersLinks link " +
            "JOIN Users u ON u.id=link.userId " +
            "LEFT OUTER JOIN Files f ON u.avatar=f.id WHERE link.chatId=?1")
    List<UsersInfo> getUsersByChatId(Long chatId);

    @Query("SELECT chat.id AS id, chat.name AS name, chat.description AS description, chat.chatType AS chatType " +
            "FROM Chats chat, ChatsUsersLinks link " +
            "WHERE chat.id=link.chatId AND link.userId=?1")
    List<ChatsInfo> getChatsByUserId(Long userId);

    ChatsUsersLinks findByChatIdAndUserId(Long chatId, Long userId);

    void deleteAllByChatId(Long chatId);
}
