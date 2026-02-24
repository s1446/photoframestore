package com.photoframestore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photoframestore.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
