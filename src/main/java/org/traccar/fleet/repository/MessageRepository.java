package org.traccar.fleet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.traccar.fleet.domain.Message;

import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Spring Data JPA repository for the Message entity.
 */
@SuppressWarnings("unused")
public interface MessageRepository extends JpaRepository<Message,Long>, PagingAndSortingRepository<Message, Long> {

    Page<Message> findAllByMessageTime(@Temporal(TemporalType.DATE) Date fromDate, Pageable pageable);

    Page<Message> findAllByMessageTimeBetween(ZonedDateTime fromDate, ZonedDateTime localDate, Pageable pageable);
}
