package com.example.networkofgiving.repositories;

import com.example.networkofgiving.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Long> {
}
