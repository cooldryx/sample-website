package com.tianwendong.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tianwendong.domain.Message;
import com.tianwendong.repository.MessageRepository;
import com.tianwendong.security.AuthoritiesConstants;
import com.tianwendong.service.MessageService;
import com.tianwendong.web.rest.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing Message.
 */
@RestController
@RequestMapping("/app")
public class MessageResource {

    private final Logger log = LoggerFactory.getLogger(MessageResource.class);

    @Inject
    private MessageService messageService;

    @Inject
    private MessageRepository messageRepository;

    /**
     * POST  /rest/messages -> Create a new message.
     */
    @RequestMapping(value = "/rest/messages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> create(@Valid @RequestBody MessageDTO messageDTO, HttpServletRequest request) {
        log.debug("REST request to save Message : {}", messageDTO);

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        messageService.saveNewMessage(messageDTO, ip, userAgent);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * GET  /rest/messages -> get all the messages.
     */
    @RequestMapping(value = "/rest/messages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Message> getAll() {
        log.debug("REST request to get all Messages");
        return messageRepository.findAll();
    }

    /**
     * GET  /rest/messages/:id -> get the "id" message.
     */
    @RequestMapping(value = "/rest/messages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Message> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Message : {}", id);
        Message message = messageRepository.findOne(id);
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/messages/:id -> delete the "id" message.
     */
    @RequestMapping(value = "/rest/messages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Message : {}", id);
        messageRepository.delete(id);
    }
}
