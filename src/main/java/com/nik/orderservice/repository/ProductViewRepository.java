package com.nik.orderservice.repository;

import com.nik.orderservice.entity.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductViewRepository extends JpaRepository<ProductView, String> {
}
