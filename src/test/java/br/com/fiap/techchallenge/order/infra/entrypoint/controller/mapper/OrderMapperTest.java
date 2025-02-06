package br.com.fiap.techchallenge.order.infra.entrypoint.controller.mapper;

import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderRequestDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderRequestDTO.OrderProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();
    }

    @Test
    @DisplayName("Should Map To Create OrderDTO")
    void shouldMapToCreateOrderDTO() {
        var customerId = UUID.randomUUID();
        var productId = UUID.randomUUID();
        var observation = "Extra cheese";

        var requestDTO = new CreateOrderRequestDTO(customerId.toString(),
                List.of(new OrderProducts(productId.toString(), observation)));

        var result = orderMapper.toCreateOrder(requestDTO);

        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        assertEquals(1, result.products().size());
        assertEquals(productId, result.products().getFirst().id());
        assertEquals(observation, result.products().getFirst().observation());
    }

    @Test
    @DisplayName("Should Handle Null CustomerId")
    void shouldHandleNullCustomerId() {
        var productId = UUID.randomUUID();
        var observation = "No onions";

        var requestDTO = new CreateOrderRequestDTO(null,
                List.of(new OrderProducts(productId.toString(), observation)));

        var result = orderMapper.toCreateOrder(requestDTO);

        assertNotNull(result);
        assertNull(result.customerId());
        assertEquals(1, result.products().size());
        assertEquals(productId, result.products().getFirst().id());
        assertEquals(observation, result.products().getFirst().observation());
    }

    @Test
    @DisplayName("Should Handle Empty Products List")
    void shouldHandleEmptyProductsList() {
        var customerId = UUID.randomUUID();
        var requestDTO = new CreateOrderRequestDTO(customerId.toString(), List.of());

        var result = orderMapper.toCreateOrder(requestDTO);

        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        assertTrue(result.products().isEmpty());
    }
}
