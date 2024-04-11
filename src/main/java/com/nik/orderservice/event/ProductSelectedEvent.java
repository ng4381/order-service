package com.nik.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSelectedEvent {
    private String orderId;
    private String productId;
    private Integer qty;
}
