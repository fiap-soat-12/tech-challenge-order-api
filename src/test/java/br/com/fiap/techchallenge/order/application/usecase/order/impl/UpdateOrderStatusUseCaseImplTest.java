package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.OrderDetails;
import br.com.fiap.techchallenge.order.domain.models.OrderTimestamps;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.CookProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.dto.CookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseImplTest {

    @Mock
    private OrderPersistence persistence;

    @Mock
    private CookProducer cookProducer;

    @InjectMocks
    private UpdateOrderStatusUseCaseImpl updateOrderStatusUseCase;

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
    @DisplayName("Should SendToCook if Order Exists And IsPaid")
    void shouldSendToCookIfOrderExistsAndIsPaid() throws JsonProcessingException {
        when(persistence.findById(orderId)).thenReturn(Optional.of(order));

        updateOrderStatusUseCase.evolveToPreparing(orderId, true);

        verify(cookProducer, times(1)).sendToCook(any(CookDTO.class));
        verify(persistence, times(1)).update(order);
    }

    @Test
    @DisplayName("Should Cancel Order if Exists And IsNotPaid")
    void shouldCancelOrderIfExistsAndIsNotPaid() throws JsonProcessingException {
        when(persistence.findById(orderId)).thenReturn(Optional.of(order));

        updateOrderStatusUseCase.evolveToPreparing(orderId, false);

        verify(persistence, times(1)).update(order);
    }

    @Test
    @DisplayName("Should Throw DoesNotExistException If Order Does Not Exist")
    void shouldThrowDoesNotExistExceptionIfOrderDoesNotExist() {
        when(persistence.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> updateOrderStatusUseCase.evolveToPreparing(orderId, true));
    }

    @Test
    @DisplayName("Should Update Order Status")
    void shouldUpdateOrderStatus() {
        when(persistence.findByStatusAndCreatedAtBefore(eq(OrderStatusEnum.RECEIVED), any(LocalDateTime.class)))
                .thenReturn(List.of(order));

        updateOrderStatusUseCase.updateOrderStatus();

        verify(persistence, times(1)).update(any(Order.class));
    }

    @Test
    @DisplayName("Should Not Call Update If No Orders To Update")
    void shouldNotCallUpdateIfNoOrdersToUpdate() {
        when(persistence.findByStatusAndCreatedAtBefore(eq(OrderStatusEnum.RECEIVED), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        updateOrderStatusUseCase.updateOrderStatus();

        verify(persistence, never()).update(any(Order.class));
    }
}
