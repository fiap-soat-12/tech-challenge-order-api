package br.com.fiap.techchallenge.order.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.gateway.database.entities.OrderEntity;
import br.com.fiap.techchallenge.order.infra.gateway.database.repository.OrderRepository;
import br.com.fiap.techchallenge.order.infra.gateway.database.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderPersistenceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private OrderPersistenceImpl orderPersistence;

    private Order order;
    private OrderEntity orderEntity;
    private UUID orderId;
    private String paymentId;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        paymentId = "12345";
        order = mock(Order.class);
        orderEntity = mock(OrderEntity.class);

        orderPersistence = new OrderPersistenceImpl(orderRepository, productRepository);

        setField(orderPersistence, entityManager);
    }

    @Test
    @DisplayName("Should Return Order When Order Exists")
    void shouldReturnOrderWhenOrderExists() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderEntity.toOrder()).thenReturn(order);

        Optional<Order> result = orderPersistence.findById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    @DisplayName("Should Return Empty When Order Doesn't Exist")
    void shouldReturnEmptyWhenOrderDoesNotExist() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = orderPersistence.findById(orderId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should Save And Return Order")
    void shouldSaveAndReturnOrder() {
        var orderEntityMock = mock(OrderEntity.class);

        when(orderEntityMock.getId()).thenReturn(orderId);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntityMock);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntityMock));
        when(orderEntityMock.toOrder()).thenReturn(order);

        doNothing().when(entityManager).clear();

        var result = orderPersistence.create(order);

        verify(entityManager).clear();
        assertEquals(order, result);
    }

    @Test
    @DisplayName("Should Save And Return Updated Order")
    void shouldSaveAndReturnUpdatedOrder() {
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderEntity.toOrder()).thenReturn(order);

        var result = orderPersistence.update(order);

        assertEquals(order, result);
    }

    @Test
    @DisplayName("Should Return List Of Orders")
    void shouldReturnListOfOrders() {
        when(orderRepository.findByStatusNot(OrderStatusEnum.PREPARING)).thenReturn(List.of(orderEntity));
        when(orderEntity.toOrder()).thenReturn(order);

        List<Order> result = orderPersistence.findByStatusNot(OrderStatusEnum.PREPARING);

        assertFalse(result.isEmpty());
        assertEquals(order, result.getFirst());
    }

    @Test
    @DisplayName("Should Return Order When Exists")
    void shouldReturnOrderWhenExists() {
        when(orderRepository.findByPaymentId(paymentId)).thenReturn(Optional.of(orderEntity));
        when(orderEntity.toOrder()).thenReturn(order);

        Optional<Order> result = orderPersistence.findByPaymentId(paymentId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    @DisplayName("Should Return Empty When Not Exists")
    void shouldReturnEmptyWhenNotExists() {
        when(orderRepository.findByPaymentId(paymentId)).thenReturn(Optional.empty());

        Optional<Order> result = orderPersistence.findByPaymentId(paymentId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should Return List Of Orders CreatedAt Before")
    void shouldReturnListOfOrdersCreatedAtBefore() {
        var createdAt = LocalDateTime.now().minusDays(1);
        when(orderRepository.findByStatusAndCreatedAtBefore(OrderStatusEnum.READY, createdAt)).thenReturn(List.of(orderEntity));
        when(orderEntity.toOrder()).thenReturn(order);

        List<Order> result = orderPersistence.findByStatusAndCreatedAtBefore(OrderStatusEnum.READY, createdAt);

        assertFalse(result.isEmpty());
        assertEquals(order, result.getFirst());
    }


    private void setField(Object target, Object value) {
        try {
            var field = target.getClass().getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
