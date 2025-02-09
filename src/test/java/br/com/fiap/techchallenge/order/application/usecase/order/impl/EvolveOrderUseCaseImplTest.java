package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.UpdateOrderStatusUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveRules;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.OrderEvolveDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvolveOrderUseCaseImplTest {

    @Mock
    private OrderPersistence persistence;

    @Mock
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Mock
    private EvolveRules evolveRule1;

    @Mock
    private EvolveRules evolveRule2;

    @InjectMocks
    private EvolveOrderUseCaseImpl evolveOrderUseCase;

    @BeforeEach
    void setUp() {
        evolveOrderUseCase = new EvolveOrderUseCaseImpl(persistence, updateOrderStatusUseCase, List.of(evolveRule1, evolveRule2));
    }

    @Test
    @DisplayName("Should Evolve To Preparing When Order Is Received And Paid")
    void shouldEvolveToPreparingWhenOrderIsReceivedAndPaid() throws JsonProcessingException {
        var orderId = UUID.randomUUID();
        var orderEvolveDTO = new OrderEvolveDTO(orderId, true);
        var order = mock(Order.class);

        when(order.getStatus()).thenReturn(OrderStatusEnum.RECEIVED);
        when(persistence.findById(orderId)).thenReturn(Optional.of(order));

        evolveOrderUseCase.evolveOrder(orderEvolveDTO);

        verify(updateOrderStatusUseCase).evolveToPreparing(orderId, true);
        verifyNoMoreInteractions(persistence);
    }

    @Test
    @DisplayName("Should Apply EvolveRules And Update Order When Order Is Not Received")
    void shouldApplyEvolveRulesAndUpdateOrderWhenOrderIsNotReceived() throws JsonProcessingException {
        var orderId = UUID.randomUUID();
        var orderEvolveDTO = new OrderEvolveDTO(orderId, false);
        var order = mock(Order.class);

        when(order.getStatus()).thenReturn(OrderStatusEnum.PREPARING);
        when(persistence.findById(orderId)).thenReturn(Optional.of(order));

        evolveOrderUseCase.evolveOrder(orderEvolveDTO);

        verify(evolveRule1).evolveTo(order);
        verify(evolveRule2).evolveTo(order);
        verify(persistence).update(order);
    }



    @Test
    @DisplayName("Should Throw Exception When Order Not Found")
    void shouldThrowExceptionWhenOrderNotFound() {
        var orderId = UUID.randomUUID();
        var orderEvolveDTO = new OrderEvolveDTO(orderId, false);

        when(persistence.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> evolveOrderUseCase.evolveOrder(orderEvolveDTO));

        verify(persistence).findById(orderId);
        verifyNoMoreInteractions(persistence, updateOrderStatusUseCase, evolveRule1, evolveRule2);
    }

}