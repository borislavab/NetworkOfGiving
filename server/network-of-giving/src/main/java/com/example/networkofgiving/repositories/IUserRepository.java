package com.example.networkofgiving.repositories;

import com.example.networkofgiving.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, String> {
}
