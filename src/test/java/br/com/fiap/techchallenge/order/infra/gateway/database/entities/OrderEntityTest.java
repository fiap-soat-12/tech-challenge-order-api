package br.com.fiap.techchallenge.order.infra.gateway.database.entities;

import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderEntityTest {

    private OrderEntity orderEntity;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        mockOrder = Mockito.mock(Order.class);
        when(mockOrder.getId()).thenReturn(UUID.randomUUID());
        when(mockOrder.getAmount()).thenReturn(BigDecimal.valueOf(100));
        when(mockOrder.getSequence()).thenReturn(1);
        when(mockOrder.getStatus()).thenReturn(OrderStatusEnum.RECEIVED);
        when(mockOrder.getIsPaid()).thenReturn(false);
        when(mockOrder.getPaymentId()).thenReturn("PAY123");
        when(mockOrder.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(mockOrder.getUpdatedAt()).thenReturn(LocalDateTime.now());

        orderEntity = new OrderEntity(mockOrder);
    }

    @Test
    @DisplayName("Should Assert Equals Mock ID and OrderEntityId")
    void shouldAssertEqualsMockIdAndOrderEntityId() {
        assertNotNull(orderEntity);
        assertEquals(mockOrder.getId(), orderEntity.getId());
    }

    @Test
    @DisplayName("Should Add OrderProductEntity")
    void shouldAddOrderProductEntity() {
        var productEntity = Mockito.mock(OrderProductEntity.class);
        orderEntity.addOrderProductEntity(productEntity);

        verify(productEntity).setOrder(orderEntity);
    }

    @Test
    @DisplayName("Should Return Order From OrderEntity")
    void shouldReturnOrderFromOrderEntity() {
        var order = orderEntity.toOrder();

        assertNotNull(order);
        assertEquals(orderEntity.getId(), order.getId());
    }
}
