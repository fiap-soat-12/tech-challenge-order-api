package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.IsPaidUseCaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IsPaidUseCaseConfigTest {

    @Mock
    private OrderPersistence orderPersistence;

    @InjectMocks
    private IsPaidUseCaseConfig isPaidUseCaseConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of IsPaidUseCaseImpl")
    void shouldCreateSingletonInstanceOfIsPaidUseCaseImpl() {
        var isPaidUseCaseImpl = isPaidUseCaseConfig.isPaidUseCaseImpl(orderPersistence);

        assertNotNull(isPaidUseCaseImpl);
        assertNotNull(orderPersistence);
        assertInstanceOf(IsPaidUseCaseImpl.class, isPaidUseCaseImpl);
    }

}