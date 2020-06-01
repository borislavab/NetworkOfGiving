package com.example.networkofgiving.repositories;

import com.example.networkofgiving.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDonationRepository extends JpaRepository<Donation, Long> {
}
