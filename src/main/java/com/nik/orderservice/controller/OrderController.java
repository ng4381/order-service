package com.nik.orderservice.controller;

import com.nik.orderservice.command.CreateOrderCommand;
import com.nik.orderservice.command.SelectProductCommand;
import com.nik.orderservice.dto.OrderViewDto;
import com.nik.orderservice.entity.OrderView;
import com.nik.orderservice.query.FindOrderQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/order/create")
    public void createOrder() {
        commandGateway.send(new CreateOrderCommand());
    }

    @PostMapping("/order/product")
    public void selectProduct(@RequestBody SelectProductCommand selectProductCommand) {
        commandGateway.send(selectProductCommand);
    }
    @GetMapping("/order/{id}")
    public CompletableFuture<OrderViewDto> getOrderViewById(@PathVariable String id) {
        return queryGateway.query(new FindOrderQuery(id), ResponseTypes.instanceOf(OrderViewDto.class));
    }
}
