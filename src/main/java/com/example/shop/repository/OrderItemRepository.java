package com.example.shop.repository;

import com.example.shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
