package com.itea.messenger.service;

import com.itea.messenger.converter.ChatsConverter;
import com.itea.messenger.converter.ChatsUsersLinksConverter;
import com.itea.messenger.converter.UsersConverter;
import com.itea.messenger.dto.ChatUsersLinksDto;
import com.itea.messenger.dto.ChatsShortDto;
import com.itea.messenger.dto.UsersShortDto;
import com.itea.messenger.entity.ChatsUsersLinks;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.interfaces.ChatsInfo;
import com.itea.messenger.interfaces.UsersInfo;
import com.itea.messenger.repository.ChatsUsersLinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultChatUsersLinksService implements ChatsUsersLinksService {
    @Autowired
    private ChatsUsersLinksRepository chatUsersLinksRepository;
    @Autowired
    private ChatsUsersLinksConverter chatUsersLinksConverter;
    @Autowired
    private UsersConverter usersConverter;
    @Autowired
    private ChatsConverter chatsConverter;

    @Override
    public ChatUsersLinksDto saveChatUsersLink(ChatsUsersLinks chatUsersLinks) {
        ChatsUsersLinks new_chatUsersLinks = chatUsersLinksRepository.save(chatUsersLinks);
        return chatUsersLinksConverter.chatUsersLinksEntityToDto(new_chatUsersLinks);
    }

    @Override
    public ChatUsersLinksDto findById(Long id) throws NotFoundException {
        ChatsUsersLinks chatUsersLinks = chatUsersLinksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("[ChatUsersLinks] record with id:" + id + " not found"));
        return chatUsersLinksConverter.chatUsersLinksEntityToDto(chatUsersLinks);
    }

    @Override
    public List<ChatUsersLinksDto> findAll(){
        List<ChatsUsersLinks> chatUsersLinksList =  chatUsersLinksRepository.findAll();
        return chatUsersLinksList.stream().map(chatUsersLinksConverter::chatUsersLinksEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<ChatsShortDto> getChatsByUserId(Long id) throws NotFoundException {
        List<ChatsInfo> chats = chatUsersLinksRepository.getChatsByUserId(id);
        if (chats.size() == 0) {
            throw new NotFoundException("[ChatUsersLinks] No chats for User id:" + id + " found");
        }
        return chats.stream().map(chatsConverter::chatEntityToShortDto).collect(Collectors.toList());
    }

    @Override
    public List<UsersShortDto> getUsersByChatId(Long id) throws NotFoundException {
        List<UsersInfo> users = chatUsersLinksRepository.getUsersByChatId(id);
        if (users.size() == 0) {
            throw new NotFoundException("[ChatUsersLinks] No users for Chat id:" + id + " found");
        }
        return users.stream().map(usersConverter::userToShortDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteAllByChatId(Long chatId) {
        chatUsersLinksRepository.deleteAllByChatId(chatId);
    }
}