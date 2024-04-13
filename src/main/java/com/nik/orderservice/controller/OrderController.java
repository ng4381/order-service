package com.nik.orderservice.controller;

import com.nik.orderservice.command.CreateOrderCommand;
import com.nik.orderservice.command.SelectProductCommand;
import com.nik.orderservice.dto.OrderViewDto;
import com.nik.orderservice.query.FindAllOrdersQuery;
import com.nik.orderservice.query.FindOrderQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/order/create")
    public ResponseEntity<String> createOrder() {
        commandGateway.send(new CreateOrderCommand());
        return ResponseEntity.ok("Order created");
    }

    @PostMapping("/order/product")
    public ResponseEntity<String> selectProduct(@RequestBody SelectProductCommand spc) {
        SelectProductCommand _selectProductCommand = new SelectProductCommand(spc.getOrderId(), spc.getProductId(), spc.getQty());
        commandGateway.send(_selectProductCommand);
        return ResponseEntity.ok("Product selected");
    }
    @GetMapping("/order/{id}")
    public CompletableFuture<OrderViewDto> getOrderViewById(@PathVariable String id) {
        return queryGateway.query(new FindOrderQuery(id), ResponseTypes.instanceOf(OrderViewDto.class));
    }

    @GetMapping("/order")
    public CompletableFuture<List<OrderViewDto>> getAllOrderViews() {
        return queryGateway.query(new FindAllOrdersQuery(), ResponseTypes.multipleInstancesOf(OrderViewDto.class));
    }
}
