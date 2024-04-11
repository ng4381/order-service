package com.nik.orderservice.repository;

import com.nik.orderservice.entity.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderViewRepository extends JpaRepository<OrderView, String> {
}
