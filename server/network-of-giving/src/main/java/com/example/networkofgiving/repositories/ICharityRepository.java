package com.example.networkofgiving.repositories;

import com.example.networkofgiving.entities.Charity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICharityRepository extends JpaRepository<Charity, Long> {
}
