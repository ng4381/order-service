package com.nik.orderservice.repository;

import com.nik.orderservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductViewRepository extends JpaRepository<Product, String> {
}
