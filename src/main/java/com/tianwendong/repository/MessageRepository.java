package com.tianwendong.repository;

import com.tianwendong.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Message entity.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

}
