package br.com.fiap.techchallenge.order.application.usecase.order.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.techchallenge.order.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.domain.models.Customer;
import br.com.fiap.techchallenge.order.domain.models.Order;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.PaymentProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.dto.PaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseImplTest {

    @Mock
    private OrderPersistence orderPersistence;

    @Mock
    private ProductPersistence productPersistence;

    @Mock
    private CustomerPersistence customerPersistence;

    @Mock
    private PaymentProducer paymentProducer;

    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;

    private UUID customerId;
    private UUID productId;
    private Customer customer;
    private CreateOrderDTO createOrderDTO;
    private CreateOrderDTO createOrderDTOWithoutCustomer;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should Create Order Successfully")
    void shouldCreateOrderSuccessfully() {
        when(customerPersistence.findById(customerId)).thenReturn(Optional.of(customer));

        when(productPersistence.findById(any(UUID.class))).thenAnswer(invocation -> {
            UUID requestedId = invocation.getArgument(0);
            return Optional.of(new Product(requestedId, "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                    new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                    LocalDateTime.now()));
        });

        when(orderPersistence.create(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var order = createOrderUseCase.create(createOrderDTO);

        assertNotNull(order);
        verify(orderPersistence).create(any(Order.class));
        verify(paymentProducer).sendToPayment(any(PaymentDTO.class));
    }

    @Test
    @DisplayName("Should Create Order Successfully Without Customer")
    void shouldCreateOrderSuccessfullyWithoutCustomer() {

        when(productPersistence.findById(any(UUID.class))).thenAnswer(invocation -> {
            UUID requestedId = invocation.getArgument(0);
            return Optional.of(new Product(requestedId, "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                    new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                    LocalDateTime.now()));
        });

        when(orderPersistence.create(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var order = createOrderUseCase.create(createOrderDTOWithoutCustomer);

        assertNotNull(order);
        verify(orderPersistence).create(any(Order.class));
        verify(paymentProducer).sendToPayment(any(PaymentDTO.class));
    }

    @Test
    @DisplayName("Should Throw DoesNotExistException When Customer Not Found")
    void shouldThrowDoesNotExistExceptionWhenCustomerNotFound() {
        when(customerPersistence.findById(customerId)).thenReturn(Optional.empty());

        var exception = assertThrows(DoesNotExistException.class, () ->
                createOrderUseCase.create(createOrderDTO)
        );

        assertEquals("Customer not found with ID: " + customerId, exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw DoesNotExistException When Product Not Found")
    void shouldThrowDoesNotExistExceptionWhenProductNotFound() {
        when(customerPersistence.findById(customerId)).thenReturn(Optional.of(customer));
        when(productPersistence.findById(productId)).thenReturn(Optional.empty());

        var exception = assertThrows(DoesNotExistException.class, () ->
                createOrderUseCase.create(createOrderDTO)
        );

        assertEquals("Product not found with ID: " + productId, exception.getMessage());
    }

    private void buildArranges(){
        customerId = UUID.randomUUID();
        productId = UUID.randomUUID();
        customer = new Customer("Walter White", "31739380037", "heisenberg@gmail.com");

        var orderProducts = List.of(new CreateOrderDTO.OrderProducts(productId, "No onions"));
        createOrderDTO = new CreateOrderDTO(customerId, orderProducts);
        createOrderDTOWithoutCustomer = new CreateOrderDTO(null, orderProducts);
    }
}
