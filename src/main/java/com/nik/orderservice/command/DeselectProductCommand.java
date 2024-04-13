package com.nik.orderservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeselectProductCommand{
    @TargetAggregateIdentifier
    private String orderId;
    private String productId;
    private Integer qty;
}
