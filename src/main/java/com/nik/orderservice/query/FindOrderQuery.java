package com.nik.orderservice.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindOrderQuery {
    private String orderId;
}
