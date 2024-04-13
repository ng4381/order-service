package com.nik.orderservice.query;


import com.nik.commonservice.event.ProductCreatedEvent;
import com.nik.orderservice.entity.Product;
import com.nik.orderservice.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductProjection {
    public final ProductViewRepository productViewRepository;
    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        log.info("[NIK]ProductCreatedEvent ... productId = " + productCreatedEvent.getProductId() + " productName = " + productCreatedEvent.getName());
        productViewRepository.save(Product.builder()
                .productId(productCreatedEvent.getProductId())
                .name(productCreatedEvent.getName())
                .build());
    }
}
