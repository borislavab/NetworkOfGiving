package com.example.networkofgiving.repositories;

import com.example.networkofgiving.entities.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUserId(Long userId, Sort sort);
}
