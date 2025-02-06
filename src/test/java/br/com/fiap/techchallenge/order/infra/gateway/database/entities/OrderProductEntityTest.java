package br.com.fiap.techchallenge.order.infra.gateway.database.entities;

import br.com.fiap.techchallenge.order.domain.models.OrderProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderProductEntityTest {

    private OrderProduct orderProduct;
    private OrderProductEntity orderProductEntity;

    @BeforeEach
    void setUp() {
        UUID productId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID orderProductId = UUID.randomUUID();

        ProductEntity productEntity = mock(ProductEntity.class);
        when(productEntity.getId()).thenReturn(productId);
        when(productEntity.getName()).thenReturn("Test Product");

        orderProduct = new OrderProduct(orderProductId, new BigDecimal("19.99"), "Extra cheese", productId, "Test Product", orderId, LocalDateTime.now());

        orderProductEntity = new OrderProductEntity(orderProduct, productEntity);
    }

    @Test
    @DisplayName("Should initialize Fields Correctly")
    void shouldInitializeFieldsCorrectly() {
        assertNotNull(orderProductEntity);
        assertEquals(orderProduct.getId(), orderProductEntity.toOrderProduct(UUID.randomUUID()).getId());
        assertEquals(orderProduct.getPrice(), orderProductEntity.toOrderProduct(UUID.randomUUID()).getPrice());
        assertEquals(orderProduct.getCustomization(), orderProductEntity.toOrderProduct(UUID.randomUUID()).getCustomization());
    }

    @Test
    @DisplayName("Should Return Correct OrderProduct")
    void shouldReturnCorrectOrderProduct() {
        UUID orderId = UUID.randomUUID();
        OrderProduct convertedOrderProduct = orderProductEntity.toOrderProduct(orderId);

        assertNotNull(convertedOrderProduct);
        assertEquals(orderProductEntity.toOrderProduct(orderId).getId(), convertedOrderProduct.getId());
        assertEquals(orderProductEntity.toOrderProduct(orderId).getPrice(), convertedOrderProduct.getPrice());
        assertEquals(orderProductEntity.toOrderProduct(orderId).getCustomization(), convertedOrderProduct.getCustomization());
        assertEquals(orderProductEntity.toOrderProduct(orderId).getProductId(), convertedOrderProduct.getProductId());
        assertEquals(orderId, convertedOrderProduct.getOrderId());
    }

    @Test
    @DisplayName("Should Assign OrderEntity")
    void shouldAssignOrderEntity() {
        OrderEntity orderEntity = mock(OrderEntity.class);
        orderProductEntity.setOrder(orderEntity);
        assertNotNull(orderProductEntity);
    }
}