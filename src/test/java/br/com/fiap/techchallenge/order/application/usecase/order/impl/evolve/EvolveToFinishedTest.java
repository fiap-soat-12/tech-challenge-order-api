package br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve;

import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EvolveToFinishedTest {

    private EvolveToFinished evolveToFinished;

    @BeforeEach
    void setUp() {
        evolveToFinished = new EvolveToFinished();
    }

    @Test
    @DisplayName("Should Set Status To Finished When Order Is Ready")
    void shouldSetStatusToFinishedWhenOrderIsReady() {
        var order = mock(Order.class);
        when(order.getStatus()).thenReturn(OrderStatusEnum.READY);

        evolveToFinished.evolveTo(order);

        verify(order).setStatusFinished();
    }

    @Test
    @DisplayName("Should Not Change Status When Order Is Not Ready")
    void shouldNotChangeStatusWhenOrderIsNotReady() {
        var order = mock(Order.class);
        when(order.getStatus()).thenReturn(OrderStatusEnum.PREPARING);

        evolveToFinished.evolveTo(order);

        verify(order, never()).setStatusFinished();
    }
}
