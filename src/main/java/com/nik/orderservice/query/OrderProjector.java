package com.nik.orderservice.query;

import com.nik.orderservice.command.IModifyOrderItems;
import com.nik.orderservice.dto.OrderViewDto;
import com.nik.orderservice.entity.Order;
import com.nik.orderservice.entity.OrderItem;
import com.nik.orderservice.entity.Product;
import com.nik.orderservice.event.OrderCreatedEvent;
import com.nik.orderservice.event.ProductDeselectedEvent;
import com.nik.orderservice.event.ProductSelectedEvent;
import com.nik.orderservice.exception.OrderNotFountException;
import com.nik.orderservice.exception.ProductNotFountException;
import com.nik.orderservice.repository.OrderItemRepository;
import com.nik.orderservice.repository.OrderViewRepository;
import com.nik.orderservice.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProjector {
    private final OrderViewRepository orderViewRepository;
    private final ProductViewRepository productViewRepository;
    private final OrderItemRepository orderItemRepository;

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        log.info("**OrderCreatedEvent was invoked!!!");
        Order order = Order.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .creationDate(LocalDateTime.now())
                //.products(Collections.EMPTY_MAP)
                .orderItems(new ArrayList<>())
                .build();
        orderViewRepository.save(order);
    }

    public void modifyProduct(IModifyOrderItems command, BiFunction<Integer, Integer, Integer> operation) throws OrderNotFountException, ProductNotFountException {

        String orderId = command.getOrderId();
        String productId = command.getProductId();
        Integer qty = command.getQty();


        var order = orderViewRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFountException("id = %s".formatted(orderId)));

        var orderItems = Optional.ofNullable(order.getOrderItems())
                .orElse(new ArrayList<>());

        var orderItem = orderItems.stream()
                .filter(item -> !Objects.isNull(item.getProduct()))
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst().orElse(null);

        if (Objects.isNull(orderItem)) {
            var product = productViewRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFountException("orderId = %s; productId = %s".formatted(orderId, productId)));

            orderItem = OrderItem.builder()
                    .product(product)
                    .qty(0)
                    .build();

            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);

        }

        orderItem.setQty(operation.apply(orderItem.getQty(), qty));

        orderViewRepository.save(order);
    }

    @EventHandler
    public void on(ProductSelectedEvent productSelectedEvent) throws OrderNotFountException, ProductNotFountException {
        modifyProduct(productSelectedEvent, Integer::sum);
    }

    @EventHandler
    public void on(ProductDeselectedEvent productDeselectedEvent) throws OrderNotFountException, ProductNotFountException {
        modifyProduct(productDeselectedEvent, (i, i2) -> i - i2);
    }

    @QueryHandler
    public OrderViewDto handle(FindOrderQuery findOrderQuery) {
        Order order = orderViewRepository.findById(findOrderQuery.getOrderId()).orElse(null);
        order.getOrderItems();

        return OrderViewDto.builder()
                .orderId(order.getOrderId())
                .creationDate(order.getCreationDate())
                .products(getProducts(order))
                .build();
    }

    @QueryHandler
    public List<OrderViewDto> handle(FindAllOrdersQuery findAllOrdersQuery) {
        List<Order> orderList = orderViewRepository.findAll();

        return orderList.stream()
                .map(order -> OrderViewDto.builder()
                        .orderId(order.getOrderId())
                        .creationDate(order.getCreationDate())
                        .products(getProducts(order))
                        .build())
                .collect(Collectors.toList());
    }

    private static String getProducts(Order order) {
        return Optional.ofNullable(order.getOrderItems())
                .map(orderItems -> orderItems.stream()
                        .map(orderItem -> " -- item: %s, qty: %s".formatted(Optional.ofNullable(orderItem.getProduct()).map(Product::getName).orElse("null"), orderItem.getQty()))
                        .collect(Collectors.joining("; "))).orElse("(no items)");
    }
}
