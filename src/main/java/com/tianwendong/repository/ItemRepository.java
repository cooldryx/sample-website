package com.tianwendong.repository;

import com.tianwendong.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Item entity.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select MAX(i.star) from Item i")
    int getMaximumStar();

    @Query("select i from Item i where i.star = ?1")
    List<Item> findByStar(int star);

    @Query("select i from Item i order by i.star desc, i.id asc")
    Page<Item> findAllOrderByStar(Pageable pageable);
}
