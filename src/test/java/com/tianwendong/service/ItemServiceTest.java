package com.tianwendong.service;

import com.tianwendong.Application;
import com.tianwendong.domain.Item;
import com.tianwendong.repository.ItemRepository;
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
public class ItemServiceTest {

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private ItemService itemService;

    @Test
    public void testGetLuckyItem() {
        List<Item> items = itemRepository.findAll();
        Item item = itemService.getLuckyItem();

        assertThat(item).isNotNull();
        assertThat(item).isIn(items);
    }
}
