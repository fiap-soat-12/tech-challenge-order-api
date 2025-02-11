package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.OrderPersistence;
import br.com.fiap.techchallenge.order.application.usecase.order.impl.FindOrderStatusUseCaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindOrderStatusUseCaseConfigTest {

    @Mock
    OrderPersistence persistence;

    @InjectMocks
    private FindOrderStatusUseCaseConfig findOrderStatusUseCaseConfig;
    
    @Test
    @DisplayName("Should Create a Singleton Instance Of FindOrderStatusUseCaseImpl")
    void shouldCreateSingletonInstanceOfFindOrderStatusUseCaseImpl() {
        var findOrderStatusUseCaseImpl = findOrderStatusUseCaseConfig.findOrderStatusUseCaseImpl(persistence);

        assertNotNull(findOrderStatusUseCaseImpl);
        assertNotNull(persistence);
        assertInstanceOf(FindOrderStatusUseCaseImpl.class, findOrderStatusUseCaseImpl);
    }

}