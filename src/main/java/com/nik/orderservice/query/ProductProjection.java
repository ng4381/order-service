package com.nik.orderservice.query;


import com.nik.commonservice.event.ProductCreatedEvent;
import com.nik.orderservice.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
//@ProcessingGroup("pr-1")
//@Order(1)
public class ProductProjection {
    public final ProductViewRepository productViewRepository;
    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        System.out.println("++++++++++++++++++++++++");
        log.info("ProductCreatedEvent ... productId = " + productCreatedEvent.getProductId() + " productName = " + productCreatedEvent.getName());
        log.info("Saving to local base ... productId = " + productCreatedEvent.getProductId());

        System.out.println("++++++++++++++++++++++++");
    }
}
