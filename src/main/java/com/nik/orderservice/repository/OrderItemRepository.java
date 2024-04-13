package com.nik.orderservice.repository;

import com.nik.orderservice.entity.OrderItem;
import com.nik.orderservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
