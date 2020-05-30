package com.example.networkofgiving.repositories;

import com.example.networkofgiving.entities.Volunteering;
import com.example.networkofgiving.entities.VolunteeringKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVolunteeringRepository extends JpaRepository<Volunteering, VolunteeringKey> {
}
