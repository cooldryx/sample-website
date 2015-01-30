package com.tianwendong.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tianwendong.domain.Item;
import com.tianwendong.repository.ItemRepository;
import com.tianwendong.security.AuthoritiesConstants;
import com.tianwendong.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Item.
 */
@RestController
@RequestMapping("/app")
public class ItemResource {

    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private ItemService itemService;

    /**
     * POST  /rest/items -> Create a new item.
     */
    @RequestMapping(value = "/rest/items",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void create(@RequestBody Item item) {
        log.debug("REST request to save Item : {}", item);
        itemRepository.save(item);
    }

    /**
     * GET  /rest/items -> get all the items.
     */
    @RequestMapping(value = "/rest/items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Item> getAll() {
        log.debug("REST request to get all Items");
        return itemRepository.findAll();
    }

    /**
     * GET  /rest/items/:id -> get the "id" item.
     */
    @RequestMapping(value = "/rest/items/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Item : {}", id);
        Item item = itemRepository.findOne(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * GET  /rest/items/lucky -> get the lucky item.
     */
    @RequestMapping(value = "/rest/items/lucky",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> get(HttpServletResponse response) {
        log.debug("REST request to get a lucky Item ");
        Item item = itemService.getLuckyItem();
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/items/:id -> delete the "id" item.
     */
    @RequestMapping(value = "/rest/items/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemRepository.delete(id);
    }
}
