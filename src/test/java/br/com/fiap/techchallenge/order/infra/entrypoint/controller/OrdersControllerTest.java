package br.com.fiap.techchallenge.order.infra.entrypoint.controller;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.usecase.order.CreateOrderUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.FindOrderStatusUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.IsPaidUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.domain.models.*;
import br.com.fiap.techchallenge.order.domain.models.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.OrderStatusResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.PaidRequestDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.handler.ControllerAdvice;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.mapper.OrderMapper;
import br.com.fiap.techchallenge.order.infra.gateway.producer.paid.PaidProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.paid.dto.PaidDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @Mock
    private IsPaidUseCase isPaidUseCase;

    @Mock
    private OrderMapper mapper;

    @Mock
    private PaidProducer paidProducer;

    @Mock
    private FindOrderStatusUseCase findOrderStatusUseCase;

    @InjectMocks
    private OrdersController orderController;

    private final String baseUrl = "/v1/orders";

    private Order order;

    private CreateOrderDTO createOrderDTO;

    private CreateOrderResponseDTO createOrderResponseDTO;

    private OrderStatusResponseDTO orderStatusResponseDTO;

    private PaidRequestDTO paidRequestDTO;

    @BeforeEach
    void setUp() {
        buildOrder();
        buildRequest();
        buildResponse();

        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("Should Create A New Order")
    void shouldCreateANewOrder() throws Exception {
        when(mapper.toCreateOrder(any())).thenReturn(createOrderDTO);
        when(createOrderUseCase.create(createOrderDTO)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(order))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(createOrderResponseDTO.orderId().toString()));
    }

    @Test
    @DisplayName("Should return NotFound when any product of order or identified customer not found")
    void shouldReturnNotFoundWhenProductOrIdentifiedCustomerNotFound() throws Exception {
        when(mapper.toCreateOrder(any())).thenReturn(createOrderDTO);
        when(createOrderUseCase.create(createOrderDTO)).thenThrow(DoesNotExistException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(order))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Return Is paid Order")
    void shouldReturnIsPaidOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(isPaidUseCase.isOrderPaid(orderId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("%s/%s/paid-status".formatted(baseUrl, orderId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should Return Is Not paid Order")
    void shouldReturnIsNotPaidOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(isPaidUseCase.isOrderPaid(orderId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("%s/%s/paid-status".formatted(baseUrl, orderId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("false")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should receive paid or not paid orders and send to queue")
    void shouldReceivePaidOrNotPaidOrdersAndSendToQueue() throws Exception {
        var orderId = UUID.randomUUID();
        doNothing().when(paidProducer).sendToPaid(new PaidDTO(orderId, paidRequestDTO.isPaid()));

        mockMvc.perform(MockMvcRequestBuilders.put("%s/%s".formatted(baseUrl, orderId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paidRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return order status")
    void shouldReturnOrderStatus() throws Exception {
        var orderId = UUID.randomUUID();
        when(findOrderStatusUseCase.findOrderStatus(orderId)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.get("%s/%s".formatted(baseUrl, orderId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderStatusResponseDTO.orderId().toString()))
                .andExpect(jsonPath("$.status").value(orderStatusResponseDTO.status().toString()));
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Adiciona suporte ao Java 8 Time API
            return objectMapper.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void buildOrder() {
        var id = UUID.randomUUID();
        var paymentId = "paymentIdMock";
        var amount = new BigDecimal("200.00");
        var status = OrderStatusEnum.PREPARING;
        var customer = new Customer(id, "Walter White", "31739380037", "heisenberg@gmail.com");
        OrderProduct orderProduct1 = new OrderProduct(UUID.randomUUID(), new BigDecimal("100.00"), "Customization 1",
                UUID.randomUUID(), "X Burger", UUID.randomUUID(), LocalDateTime.now());
        OrderProduct orderProduct2 = new OrderProduct(UUID.randomUUID(), new BigDecimal("100.00"), "Customization 2",
                UUID.randomUUID(), "X Bacon", UUID.randomUUID(), LocalDateTime.now());
        var products = List.of(orderProduct1, orderProduct2);

        order = new Order(id, amount, new OrderDetails(2, status, true, products, customer, paymentId),
                new OrderTimestamps(LocalDateTime.now(), LocalDateTime.now()));
    }

    private void buildRequest() {
        CreateOrderDTO.OrderProducts product = new CreateOrderDTO.OrderProducts(UUID.randomUUID(), "mock observation");
        createOrderDTO = new CreateOrderDTO(UUID.randomUUID(), List.of(product, product));

        paidRequestDTO = new PaidRequestDTO( true);
    }

    private void buildResponse() {
        createOrderResponseDTO = new CreateOrderResponseDTO(order.getId(), order.getSequence());
        orderStatusResponseDTO = new OrderStatusResponseDTO(order.getId(), order.getSequence(), order.getStatus());
    }

}