package com.nik.orderservice.command;

public interface IModifyOrderItems {
    String getOrderId();
    String getProductId();
    Integer getQty();
}
