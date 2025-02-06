package br.com.fiap.techchallenge.order.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductTest {

    private UUID id;

    private UUID productId;

    private String customization;

    private OrderProduct orderProduct;


    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should return OrderProduct attributes as the object was created without ID")

    void shouldInstantiateOrderProductConstructor() {
        var price = BigDecimal.valueOf(100.00);

        assertEquals(id, orderProduct.getId());
        assertEquals(price, orderProduct.getPrice());
        assertEquals(customization, orderProduct.getCustomization());
        assertEquals(productId, orderProduct.getProductId());
    }

    @Test
    @DisplayName("Should return OrderProducts attributes as the object was created")
    void shouldCreateOrderProduct() {
        var price = BigDecimal.valueOf(99.99);

        orderProduct = OrderProduct.create(price, customization, productId);

        assertNull(orderProduct.getId());
        assertEquals(price, orderProduct.getPrice());
        assertEquals(customization, orderProduct.getCustomization());
        assertEquals(productId, orderProduct.getProductId());
    }

    private void buildArranges() {
        this.id = UUID.randomUUID();
        this.productId = UUID.randomUUID();
        this.customization = "Extra cheese";

        orderProduct = new OrderProduct(id, BigDecimal.valueOf(100.00), customization, productId, "X-Burger",
                UUID.randomUUID(), LocalDateTime.now());
    }

}