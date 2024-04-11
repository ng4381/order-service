package com.nik.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderView {
    @Id
    //@UuidGenerator
    private String orderId;
    private LocalDateTime creationDate;
    @ElementCollection
    private Map<String, Integer> products;
}
