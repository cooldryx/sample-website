package com.tianwendong.service;

import com.tianwendong.Application;
import com.tianwendong.domain.Message;
import com.tianwendong.repository.MessageRepository;
import com.tianwendong.web.rest.dto.MessageDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the ItemService manager.
 *
 * @see com.tianwendong.service.ItemService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class MessageServiceTest {

    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";

    private static final String DEFAULT_NICKNAME = "SAMPLE_TEXT";

    private static final String DEFAULT_CONTENT = "SAMPLE_TEXT";

    private static final String DEFAULT_IP_ADDRESS = "SAMPLE_TEXT";

    private static final String DEFAULT_USER_AGENT = "SAMPLE_TEXT";

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private MessageService messageService;

    private MessageDTO messageDTO;

    @Before
    public void initTest() {
        messageDTO = new MessageDTO(DEFAULT_EMAIL, DEFAULT_NICKNAME, DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void testSaveNewMessage() {
        messageRepository.deleteAll();
        messageService.saveNewMessage(messageDTO, DEFAULT_IP_ADDRESS, DEFAULT_USER_AGENT);

        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(1);
        Message testMessage = messages.iterator().next();
        assertThat(testMessage.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMessage.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testMessage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testMessage.getCreatedTime()).isNotNull();
        assertThat(testMessage.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testMessage.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
    }
}
