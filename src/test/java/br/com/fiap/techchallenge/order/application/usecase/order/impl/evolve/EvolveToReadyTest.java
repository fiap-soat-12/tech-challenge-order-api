package br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EvolveToReadyTest {

    private EvolveToReady evolveToReady;

    @BeforeEach
    void setUp() {
        evolveToReady = new EvolveToReady();
    }

    @Test
    @DisplayName("Should Set Status To Ready When Order Is Preparing")
    void shouldSetStatusToReadyWhenOrderIsPreparing() {
        var order = mock(Order.class);
        when(order.getStatus()).thenReturn(OrderStatusEnum.PREPARING);

        evolveToReady.evolveTo(order);

        verify(order, times(1)).setStatusReady();
    }

    @Test
    @DisplayName("Should Not Change Status When Order Is Not Preparing")
    void shouldNotChangeStatusWhenOrderIsNotPreparing() {
        var order = mock(Order.class);
        when(order.getStatus()).thenReturn(OrderStatusEnum.RECEIVED);

        evolveToReady.evolveTo(order);

        verify(order, never()).setStatusReady();
    }
}