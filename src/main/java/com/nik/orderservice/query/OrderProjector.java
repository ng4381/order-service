package com.nik.orderservice.query;

import com.nik.orderservice.entity.OrderView;
import com.nik.orderservice.event.OrderCreatedEvent;
import com.nik.orderservice.event.ProductSelectedEvent;
import com.nik.orderservice.repository.OrderViewRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class OrderProjector {
    private final OrderViewRepository orderViewRepository;

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        OrderView orderView = OrderView.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .creationDate(LocalDateTime.now())
                .products(Collections.EMPTY_MAP)
                .build();
        orderViewRepository.save(orderView);
    }

    @EventHandler
    public void on(ProductSelectedEvent productSelectedEvent) {
        OrderView orderView = orderViewRepository.findById(productSelectedEvent.getOrderId()).orElse(null);
        if (orderView == null) {
            return;
        }
        var products = orderView.getProducts();
        if (products == null) {
            orderView.setProducts(new HashMap<>());
        }
        products.merge(productSelectedEvent.getProductId(), productSelectedEvent.getQty(), Integer::sum);
        orderViewRepository.save(orderView);
    }

    @QueryHandler
    public OrderView handle(FindOrderQuery findOrderQuery) {
        return orderViewRepository.findById(findOrderQuery.getOrderId()).orElse(null);
    }
}
