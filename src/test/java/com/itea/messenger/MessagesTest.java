package com.itea.messenger;

import com.itea.messenger.controller.MessagesController;
import com.itea.messenger.converter.MessagesConverter;
import com.itea.messenger.dto.MessagesDto;
import com.itea.messenger.entity.Messages;
import com.itea.messenger.repository.MessagesRepository;
import com.itea.messenger.service.DefaultMessagesService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest({MessagesController.class, DefaultMessagesService.class, MessagesConverter.class})
@JsonTest
public class MessagesTest {
    private final String TEST_TEXT = "This is the test message.";
    private MessagesConverter messagesConverter = new MessagesConverter();
    private LocalDateTime ldt;

/*
//
    @Mock
    public MessagesDto mockMessageDto;
//    ������ ��� �� ������, ������ mockMessageDto = null. ����� ���, �� �����
*/
    MessagesDto mockMessageDto = mock(MessagesDto.class);
    @MockBean
    private MessagesRepository messagesRepository;

    @Autowired
    private JacksonTester<MessagesDto> jsonMessageDto;
/*
    @Autowired
    private DefaultMessagesService messagesService;
*/
/*
    @Autowired
    private MockMvc mockMvc;
*/

    @Test
    @DisplayName("Convert message to DTO")
    public void testMessagesToDto() {
//        ldt = LocalDateTime.now();
//        MessagesDto messageDto = messagesConverter.messagesToDto(new Messages(2L, 3L, 4L, TEST_TEXT, 5L, ldt));
        MessagesDto messageDto = messagesConverter.messagesToDto(new Messages(3L, 4L, TEST_TEXT, 5L));
//        assertEquals(2L, messageDto.getId());
        assertEquals(3L, messageDto.getChatId());
        assertEquals(4L, messageDto.getUserId());
        assertEquals(5L, messageDto.getFileId());
        assertEquals(TEST_TEXT, messageDto.getMessageText());
//        assertEquals(ldt, messageDto.getDateTime());
    }

    @Test
    @DisplayName("Convert message from DTO")
    public void testMessagesFromDto() {
        ldt = LocalDateTime.now();
        when(mockMessageDto.getId()).thenReturn(5L);
//        doReturn(5L).when(mockMessageDto).getId();
        when(mockMessageDto.getChatId()).thenReturn(4L);
        when(mockMessageDto.getUserId()).thenReturn(3L);
        when(mockMessageDto.getFileId()).thenReturn(2L);
        when(mockMessageDto.getMessageText()).thenReturn(TEST_TEXT);
        when(mockMessageDto.getDateTime()).thenReturn(ldt);
        Messages message = messagesConverter.messagesFromDto(mockMessageDto);
        assertEquals(5L, message.getId());
        assertEquals(4L, message.getChatId());
        assertEquals(3L, message.getUserId());
        assertEquals(2L, message.getFileId());
        assertEquals(TEST_TEXT, message.getMessageText());
        assertEquals(ldt, message.getDateTime());
        verify(mockMessageDto, times(1)).getId();
        verify(mockMessageDto, times(1)).getChatId();
        verify(mockMessageDto, times(1)).getFileId();
        verify(mockMessageDto, times(1)).getUserId();
        verify(mockMessageDto, times(1)).getMessageText();
        verify(mockMessageDto, times(1)).getDateTime();
    }

    @Disabled("Need to correct actual JsonContent")
    @Test
    @DisplayName("Serialize DTO to JSON, should be equal")
    public void testSerializeMessage() throws Exception {
        ldt = LocalDateTime.now();
        when(mockMessageDto.getId()).thenReturn(5L);
        when(mockMessageDto.getChatId()).thenReturn(4L);
        when(mockMessageDto.getUserId()).thenReturn(3L);
        when(mockMessageDto.getFileId()).thenReturn(2L);
        when(mockMessageDto.getMessageText()).thenReturn(TEST_TEXT);
        when(mockMessageDto.getDateTime()).thenReturn(ldt);
//  TODO - correct actual string or JSON answer
        String actual = "JsonContent {\"id\":5,\"chatId\":4,\"userId\":3,\"messageText\":\"This is the test message.\",\"fileId\":2,\"dateTime\":\"" + ldt + "\"}";
        System.out.println(jsonMessageDto.write(mockMessageDto));
        assertEquals(jsonMessageDto.write(mockMessageDto).toString(), actual);
    }

    @Disabled("Need to correct Json DateTime format!")
    @Test
    @DisplayName("Deserialize JSON to DTO, should be equal")
    public void testDeserializeMessage() throws Exception {
        File file = new File("src/test/resources/json-message-deserialize.json");
        MessagesDto messageDto = jsonMessageDto.read(file).getObject();
        assertEquals(messageDto.getId(), 10L);
        assertEquals(messageDto.getChatId(), 2L);
        assertEquals(messageDto.getUserId(), 3L);
        assertEquals(messageDto.getFileId(), 4L);
        assertEquals(messageDto.getMessageText(), "Json test new message");
        assertEquals(messageDto.getDateTime().toString(), "2022-08-18T08:53:05.571Z");
    }

//    Controllers layer tests
    @Test
    public void testMessageControllerGetMessageById() throws Exception {
        ldt = LocalDateTime.now();
//        when(messagesRepository.findById(any())).thenReturn(java.util.Optional.of(new Messages(2L, 3L, 4L, TEST_TEXT, 5L, ldt)));
        when(messagesRepository.findById(any())).thenReturn(java.util.Optional.of(new Messages(3L, 4L, TEST_TEXT, 5L)));
//        System.out.println(messagesRepository.findById(5L));
/*
        mockMvc.perform(get("/person/42").andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        varify
*/
/*
        1. ����� Spring Boot c ������� @WebMvcTest(PersonController.class) ������
        �������� ��������� � ����������� Spring MVC � �������� � ���� Jackson,
        ������ ������ � ��� ����, � ����� ��� ��������� � �������� ����������.
        2. ����� �� ������ ������ � ������� mockMvc, ������, ��� ����� �����������.
        3. � ����� �� ����� ����� mat�her-��, ������� ��������� ������, ����� �����
        ��������� ����� � �������, � HTTP-���������.
        - ��� ������ ������������� ����� ��������� � View-���� SpringBoot ����������.
        � ��� ��������� DAO/Repository-����, �� ������ � ��������� �������.
*/
    }

//    Services layer tests
    @Test
    public void testMessagesServiceGetMessageById() {
        ldt = LocalDateTime.now();
//        when(messagesRepository.findById(any())).thenReturn(java.util.Optional.of(new Messages(8L, 10L, 11L, TEST_TEXT, 5L, ldt)));
/*
        MessagesDto messagesDto = messagesService.getMessageById(2L);
        System.out.println(messagesDto);
*/
    }


}
