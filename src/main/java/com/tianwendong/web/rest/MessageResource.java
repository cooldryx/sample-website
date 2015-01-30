package com.tianwendong.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tianwendong.domain.Message;
import com.tianwendong.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Message.
 */
@RestController
@RequestMapping("/app")
public class MessageResource {

    private final Logger log = LoggerFactory.getLogger(MessageResource.class);

    @Inject
    private MessageRepository messageRepository;

    /**
     * POST  /rest/messages -> Create a new message.
     */
    @RequestMapping(value = "/rest/messages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Message message) {
        log.debug("REST request to save Message : {}", message);
        messageRepository.save(message);
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
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Message : {}", id);
        messageRepository.delete(id);
    }
}
