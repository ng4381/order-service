package com.nik.orderservice.command;

import com.nik.commonservice.event.OrderConfirmedEvent;
import com.nik.orderservice.event.OrderCreatedEvent;
import com.nik.orderservice.event.ProductDeselectedEvent;
import com.nik.orderservice.event.ProductSelectedEvent;
import com.nik.orderservice.exception.ProductDeselectionException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aggregate
@Slf4j
public class Order {
    @AggregateIdentifier
    private String orderId;
    private Map<String, Integer> selectedProducts;

    public Order() {
    }

    @CommandHandler
    public Order(CreateOrderCommand createOrderCommand) {
        String newOrderId = UUID.randomUUID().toString();
        log.info("CreateOrderCommand ... id = " + newOrderId);
        AggregateLifecycle.apply(new OrderCreatedEvent(newOrderId));
    }

    @CommandHandler
    public void handle(SelectProductCommand selectProductCommand) {
        AggregateLifecycle.apply(new ProductSelectedEvent(selectProductCommand.getOrderId(), selectProductCommand.getProductId(), selectProductCommand.getQty()));
    }

    @CommandHandler
    public void handle(DeselectProductCommand deselectProductCommand) throws ProductDeselectionException {
        if (!selectedProducts.containsKey(deselectProductCommand.getProductId())) {
            throw new ProductDeselectionException();
        }
        AggregateLifecycle.apply(new ProductDeselectedEvent(deselectProductCommand.getOrderId(), deselectProductCommand.getProductId(), deselectProductCommand.getQty()));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand confirmOrderCommand) {
        AggregateLifecycle.apply(new OrderConfirmedEvent(confirmOrderCommand.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        orderId = orderCreatedEvent.getOrderId();
        selectedProducts = new HashMap<>();
        log.info("[NIK]EventSourcingHandler OrderCreatedEvent ... id = " + orderId);
    }

    @EventSourcingHandler
    public void on(ProductSelectedEvent productSelectedEvent) {
        selectedProducts.merge(productSelectedEvent.getProductId(), productSelectedEvent.getQty(), Integer::sum);
        log.info("[NIK]EventSourcingHandler productSelectedEvent ... id = " + orderId);
    }

    @EventSourcingHandler
    public void on(ProductDeselectedEvent productDeselectedEvent) {
        selectedProducts.merge(productDeselectedEvent.getProductId(), productDeselectedEvent.getQty(), (i1, i2) -> i1 - i2);
        log.info("[NIK]EventSourcingHandler productDeselectedEvent ... id = " + orderId);
    }
}
