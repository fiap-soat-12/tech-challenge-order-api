package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.CreateOrderUseCaseImpl;
import br.com.fiap.techchallenge.order.infra.gateway.producer.payment.PaymentProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseConfigTest {

    @Mock
    OrderPersistence orderPersistence;

    @Mock
    ProductPersistence productPersistence;

    @Mock
    CustomerPersistence customerPersistence;

    @Mock
    PaymentProducer paymentProducer;


    @InjectMocks
    private CreateOrderUseCaseConfig createOrderUseCaseConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of CreateOrderUseCaseImpl")
    void shouldCreateSingletonInstanceOfCreateOrderUseCaseImpl() {
        var createOrderUseCaseImpl = createOrderUseCaseConfig.createOrderUseCaseImpl(orderPersistence,
                productPersistence, customerPersistence, paymentProducer);

        assertNotNull(createOrderUseCaseImpl);
        assertNotNull(orderPersistence);
        assertNotNull(productPersistence);
        assertNotNull(customerPersistence);
        assertNotNull(paymentProducer);
        assertInstanceOf(CreateOrderUseCaseImpl.class, createOrderUseCaseImpl);
    }

}