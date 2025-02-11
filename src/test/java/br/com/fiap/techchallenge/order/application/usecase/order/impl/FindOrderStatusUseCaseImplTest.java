package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.OrderDetails;
import br.com.fiap.techchallenge.order.domain.models.OrderTimestamps;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindOrderStatusUseCaseImplTest {

    @Mock
    private OrderPersistence persistence;

    @InjectMocks
    private FindOrderStatusUseCaseImpl findOrderStatusUseCase;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Order(orderId, BigDecimal.valueOf(100),
                new OrderDetails(1, OrderStatusEnum.RECEIVED, true, Collections.emptyList(), null, "payment123"),
                new OrderTimestamps(LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    @DisplayName("Should return Order Found")
    void shouldReturnOrderFound() {
        when(persistence.findById(orderId)).thenReturn(Optional.of(order));

        var orderReturned = findOrderStatusUseCase.findOrderStatus(orderId);

        assertEquals(orderReturned.getId(), order.getId());
        assertEquals(orderReturned.getSequence(), order.getSequence());
        assertEquals(orderReturned.getStatus(), order.getStatus());
        verify(persistence, times(1)).findById(orderId);
    }
}