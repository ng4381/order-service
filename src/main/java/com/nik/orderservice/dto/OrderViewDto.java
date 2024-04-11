package com.nik.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class OrderViewDto {
    private String orderId;
    private LocalDateTime creationDate;
    private Map<String, Integer> products;
}
