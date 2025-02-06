package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.UpdateOrderStatusUseCaseImpl;
import br.com.fiap.techchallenge.order.infra.gateway.producer.cook.CookProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseConfigTest {

    @Mock
    private OrderPersistence orderPersistence;

    @Mock
    private CookProducer cookProducer;

    @InjectMocks
    private UpdateOrderStatusUseCaseConfig orderStatusUseCaseConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of UpdateOrderStatusUseCaseImpl")
    void shouldCreateSingletonInstanceOfUpdateOrderStatusUseCaseImpl() {
        var updateOrderStatusUseCaseImpl = orderStatusUseCaseConfig.updateOrderStatusUseCaseImpl(orderPersistence, cookProducer);

        assertNotNull(updateOrderStatusUseCaseImpl);
        assertNotNull(orderPersistence);
        assertNotNull(cookProducer);
        assertInstanceOf(UpdateOrderStatusUseCaseImpl.class, updateOrderStatusUseCaseImpl);
    }

}