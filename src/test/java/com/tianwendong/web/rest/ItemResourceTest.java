package com.tianwendong.web.rest;

import org.junit.Before;
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
import java.util.List;

import com.tianwendong.Application;
import com.tianwendong.domain.Item;
import com.tianwendong.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemResource REST controller.
 *
 * @see ItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ItemResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_STAR = 0;
    private static final Integer UPDATED_STAR = 1;

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final String DEFAULT_PATH = "SAMPLE_TEXT";
    private static final String UPDATED_PATH = "UPDATED_TEXT";


    @Inject
    private ItemRepository itemRepository;

    private MockMvc restItemMockMvc;

    private Item item;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemResource itemResource = new ItemResource();
        ReflectionTestUtils.setField(itemResource, "itemRepository", itemRepository);
        this.restItemMockMvc = MockMvcBuilders.standaloneSetup(itemResource).build();
    }

    @Before
    public void initTest() {
        item = new Item();
        item.setName(DEFAULT_NAME);
        item.setStar(DEFAULT_STAR);
        item.setDescription(DEFAULT_DESCRIPTION);
        item.setPath(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createItem() throws Exception {
        // find current table size
        int size = itemRepository.findAll().size();

        // Create the Item
        restItemMockMvc.perform(post("/app/rest/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(size + 1);
        Item testItem = items.get(size);
        assertThat(testItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItem.getStar()).isEqualTo(DEFAULT_STAR);
        assertThat(testItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItem.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.deleteAll();
        itemRepository.saveAndFlush(item);

        // Get all the items
        restItemMockMvc.perform(get("/app/rest/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(item.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].star").value(DEFAULT_STAR))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(get("/app/rest/items/{id}", item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(item.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.star").value(DEFAULT_STAR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItem() throws Exception {
        // find current table size
        int size = itemRepository.findAll().size();

        // Get the item
        restItemMockMvc.perform(get("/app/rest/items/{id}", (long) (size + 1)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItem() throws Exception {
        // find current table size
        int size = itemRepository.findAll().size();

        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Update the item
        item.setName(UPDATED_NAME);
        item.setStar(UPDATED_STAR);
        item.setDescription(UPDATED_DESCRIPTION);
        item.setPath(UPDATED_PATH);
        restItemMockMvc.perform(post("/app/rest/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(size + 1);
        Item testItem = items.get(size);
        assertThat(testItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItem.getStar()).isEqualTo(UPDATED_STAR);
        assertThat(testItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItem.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        // find current table size
        int size = itemRepository.findAll().size();

        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(delete("/app/rest/items/{id}", item.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(size);
    }
}
