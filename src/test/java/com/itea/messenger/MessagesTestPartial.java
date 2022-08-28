package com.itea.messenger;


import com.itea.messenger.controller.MessagesController;
import com.itea.messenger.converter.MessagesConverter;
import com.itea.messenger.entity.Messages;
import com.itea.messenger.repository.MessagesRepository;
import com.itea.messenger.service.DefaultMessagesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@WebMvcTest({MessagesController.class, DefaultMessagesService.class, MessagesConverter.class})
//@JsonTest
public class MessagesTestPartial {
    private final String TEST_TEXT = "This is the test message.";
    private MessagesConverter messagesConverter = new MessagesConverter();
    private LocalDateTime ldt;

/*
    MessagesDto mockMessageDto = mock(MessagesDto.class);
*/
    @MockBean
    private MessagesRepository messagesRepository;

/*
    @Autowired
    private JacksonTester<MessagesDto> jsonMessageDto;
    @Autowired
    private MockMvc mockMvc;
*/

    @Test
    public void testMessageControllerGetMessageById() {
        ldt = LocalDateTime.now();
        when(messagesRepository.findById(any())).thenReturn(java.util.Optional.of(new Messages(2L, 3L, 4L, TEST_TEXT, 5L, ldt)));
        System.out.println(messagesRepository.findById(5L));
    }

}
