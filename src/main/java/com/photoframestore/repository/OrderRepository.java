package com.photoframestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photoframestore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
