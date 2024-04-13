package com.nik.orderservice.repository;

import com.nik.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderViewRepository extends JpaRepository<Order, String> {
}
