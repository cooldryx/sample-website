package com.tianwendong.service;

import com.tianwendong.domain.Message;
import com.tianwendong.repository.MessageRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service class for managing messages.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private MessageRepository messageRepository;

    public void saveNewMessage(String email, String nickname, String content, String ipAddress, String userAgent) {
        Message newMessage = new Message();
        newMessage.setEmail(email);
        newMessage.setNickname(nickname);
        newMessage.setContent(content);
        newMessage.setIpAddress(ipAddress);
        newMessage.setUserAgent(userAgent);

        messageRepository.save(newMessage);
        log.debug("Created a new message: {}", newMessage);
    }
}
