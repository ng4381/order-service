package com.nik.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    //@UuidGenerator
    private String orderId;
    private LocalDateTime creationDate;
    //    @ElementCollection(fetch = FetchType.EAGER)
//    private Map<String, Integer> products;
    //@OneToMany(fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;
}
