package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.domain.models.*;
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
class IsPaidUseCaseImplTest {

    @Mock
    private OrderPersistence orderPersistence;

    @InjectMocks
    private IsPaidUseCaseImpl isPaidUseCase;

    private UUID orderId;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Should Return True When Order Is Paid")
    void shouldReturnTrueWhenOrderIsPaid() {
        when(orderPersistence.findById(orderId)).thenReturn(Optional.of(this.buildOrderPaid()));

        var result = isPaidUseCase.isOrderPaid(orderId);

        assertTrue(result);
        verify(orderPersistence, times(1)).findById(orderId);
    }

    @Test
    @DisplayName("Should Return False When Order Is Not Paid")
    void shouldReturnFalseWhenOrderIsNotPaid() {
        when(orderPersistence.findById(orderId)).thenReturn(Optional.of(this.buildOrderUnpaid()));

        var result = isPaidUseCase.isOrderPaid(orderId);

        assertFalse(result);
        verify(orderPersistence, times(1)).findById(orderId);
    }

    @Test
    @DisplayName("Should Throw DoesNotExistException When Order Does Not Exist")
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        when(orderPersistence.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> isPaidUseCase.isOrderPaid(orderId));
        verify(orderPersistence, times(1)).findById(orderId);
    }

    private Order buildOrderPaid() {
        return new Order(orderId, BigDecimal.valueOf(100),
                new OrderDetails(1, OrderStatusEnum.RECEIVED, true, Collections.emptyList(), null, "payment123"),
                new OrderTimestamps(LocalDateTime.now(), LocalDateTime.now()));
    }

    private Order buildOrderUnpaid() {
        return new Order(orderId, BigDecimal.valueOf(100),
                new OrderDetails(1, OrderStatusEnum.RECEIVED, false, Collections.emptyList(), null, "payment123"),
                new OrderTimestamps(LocalDateTime.now(), LocalDateTime.now()));
    }

}