package com.nik.orderservice;

import com.nik.orderservice.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyEventHandler {
    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        log.info("--> OrderCreatedEvent  id = " + orderCreatedEvent.getOrderId());
        //log.info("--> EventHandler EventHandler... ");
    }
}
