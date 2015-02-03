package com.tianwendong.web.rest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import com.tianwendong.Application;
import com.tianwendong.domain.Message;
import com.tianwendong.repository.MessageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MessageResource REST controller.
 *
 * @see MessageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Ignore public class MessageResourceTest {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    private static final String DEFAULT_NICKNAME = "SAMPLE_TEXT";
    private static final String UPDATED_NICKNAME = "UPDATED_TEXT";

    private static final String DEFAULT_CONTENT = "SAMPLE_TEXT";
    private static final String UPDATED_CONTENT = "UPDATED_TEXT";

    private static final DateTime DEFAULT_CREATED_TIME = new DateTime(0L);
    private static final DateTime UPDATED_CREATED_TIME = new DateTime().withMillisOfSecond(0);
    private static final String DEFAULT_CREATED_TIME_STR = dateTimeFormatter.print(DEFAULT_CREATED_TIME);

    private static final String DEFAULT_IP_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_IP_ADDRESS = "UPDATED_TEXT";

    private static final String DEFAULT_USER_AGENT = "SAMPLE_TEXT";
    private static final String UPDATED_USER_AGENT = "UPDATED_TEXT";


    @Inject
    private MessageRepository messageRepository;

    private MockMvc restMessageMockMvc;

    private Message message;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MessageResource messageResource = new MessageResource();
        ReflectionTestUtils.setField(messageResource, "messageRepository", messageRepository);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource).build();
    }

    @Before
    public void initTest() {
        message = new Message();
        message.setEmail(DEFAULT_EMAIL);
        message.setNickname(DEFAULT_NICKNAME);
        message.setContent(DEFAULT_CONTENT);
        message.setCreatedTime(DEFAULT_CREATED_TIME);
        message.setIpAddress(DEFAULT_IP_ADDRESS);
        message.setUserAgent(DEFAULT_USER_AGENT);
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        // Validate the database is empty
        assertThat(messageRepository.findAll()).hasSize(0);

        // Create the Message
        restMessageMockMvc.perform(post("/app/rest/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(1);
        Message testMessage = messages.iterator().next();
        assertThat(testMessage.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMessage.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testMessage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testMessage.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testMessage.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testMessage.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messages
        restMessageMockMvc.perform(get("/app/rest/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(message.getId().intValue()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].nickname").value(DEFAULT_NICKNAME.toString()))
                .andExpect(jsonPath("$.[0].content").value(DEFAULT_CONTENT.toString()))
                .andExpect(jsonPath("$.[0].createdTime").value(DEFAULT_CREATED_TIME_STR))
                .andExpect(jsonPath("$.[0].ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].userAgent").value(DEFAULT_USER_AGENT.toString()));
    }

    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(get("/app/rest/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME_STR))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/app/rest/messages/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Update the message
        message.setEmail(UPDATED_EMAIL);
        message.setNickname(UPDATED_NICKNAME);
        message.setContent(UPDATED_CONTENT);
        message.setCreatedTime(UPDATED_CREATED_TIME);
        message.setIpAddress(UPDATED_IP_ADDRESS);
        message.setUserAgent(UPDATED_USER_AGENT);
        restMessageMockMvc.perform(post("/app/rest/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(1);
        Message testMessage = messages.iterator().next();
        assertThat(testMessage.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMessage.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testMessage.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testMessage.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testMessage.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testMessage.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(delete("/app/rest/messages/{id}", message.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(0);
    }
}
