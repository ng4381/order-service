package com.nik.orderservice.event;

import com.nik.orderservice.command.IModifyOrderItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSelectedEvent implements IModifyOrderItems {
    private String orderId;
    private String productId;
    private Integer qty;
}
