package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.UpdateOrderStatusUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.EvolveOrderUseCaseImpl;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.UpdateOrderStatusUseCaseImpl;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToFinished;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToReady;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EvolveOrderUseCaseConfigTest {

    @Mock
    private OrderPersistence orderPersistence;

    @Mock
    private EvolveToFinished evolveToFinished;

    @Mock
    UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Mock
    EvolveToReady evolveToReady;

    @InjectMocks
    private EvolveOrderUseCaseConfig evolveOrderUseCaseConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of EvolveOrderUseCaseImpl")
    void shouldCreateSingletonInstanceOfEvolveOrderUseCaseImpl() {
        var evolveOrderUseCase = evolveOrderUseCaseConfig.evolveOrderUseCase(orderPersistence, evolveToFinished, updateOrderStatusUseCase, evolveToReady);

        assertNotNull(evolveOrderUseCase);
        assertNotNull(orderPersistence);
        assertNotNull(evolveToFinished);
        assertNotNull(updateOrderStatusUseCase);
        assertNotNull(evolveToReady);
        assertInstanceOf(EvolveOrderUseCaseImpl.class, evolveOrderUseCase);
    }

}