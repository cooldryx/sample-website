package com.tianwendong.service;

import com.tianwendong.domain.Item;
import com.tianwendong.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * Service class for managing items.
 */
@Service
@Transactional
public class ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);

    @Inject
    private ItemRepository itemRepository;

    public Page<Item> getAllItems(Pageable pageable) {
        return itemRepository.findAllOrderByStar(pageable);
    }

    public Item getLuckyItem() {
        log.debug("Start generating a random star for item");

        Random rand = new Random();
        int maxStar = itemRepository.getMaximumStar();
        int totalWeight = (maxStar + 1) * maxStar;
        float randomForStar = rand.nextFloat() * totalWeight;
        int finalStar = -1;

        for (int i = 1; i <= maxStar ; i++) {
            randomForStar -= (maxStar - i + 1) * 2;
            if (randomForStar <= 0.0f) {
                finalStar = i;
                break;
            }
        }

        log.debug("Get a {} star item", finalStar);

        List<Item> items = itemRepository.findByStar(finalStar);
        int length = items.size();
        int id = rand.nextInt(length);

        return items.get(id);
    }
}
