package com.example.networkofgiving.repositories;

import com.example.networkofgiving.entities.Charity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICharityRepository extends JpaRepository<Charity, Long> {
    List<Charity> findByTitleContaining(String title);
}
